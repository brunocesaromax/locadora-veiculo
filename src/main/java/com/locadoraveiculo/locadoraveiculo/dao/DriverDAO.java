package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.info.DriverInfo;
import com.locadoraveiculo.locadoraveiculo.model.Driver;
import com.locadoraveiculo.locadoraveiculo.model.Rent;
import com.locadoraveiculo.locadoraveiculo.model.Rent_;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DriverDAO {

    @PersistenceContext
    private final EntityManager em;

    public Driver save(Driver driver) {
        em.merge(driver);
        return driver;
    }

    public List<Driver> findAll() {
        return em.createQuery("select d from Driver d", Driver.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Driver driverTemp = em.find(Driver.class, id);

        em.remove(driverTemp);
        em.flush();
    }

    public Driver findById(Long driverId) {
        return em.find(Driver.class, driverId);
    }

    public List<DriverInfo> findTopFive() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<DriverInfo> criteriaQuery = builder.createQuery(DriverInfo.class);

        Root<Rent> root = criteriaQuery.from(Rent.class);

        criteriaQuery.select(builder.construct(DriverInfo.class,
                root.get(Rent_.DRIVER),
                builder.count(root.get(Rent_.ID))));

        Calendar onStart = Calendar.getInstance();
        onStart.add(Calendar.DAY_OF_MONTH, -30);

        Predicate predicate = builder.between(root.get(Rent_.REQUEST_DATE), onStart, Calendar.getInstance());

        criteriaQuery.where(predicate);
        criteriaQuery.groupBy(root.get(Rent_.DRIVER));
        criteriaQuery.orderBy(builder.desc(builder.count(root.get(Rent_.ID))));

        TypedQuery<DriverInfo> query = em.createQuery(criteriaQuery);

        return query.setMaxResults(5).getResultList();
    }
}

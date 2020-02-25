package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Car;
import com.locadoraveiculo.locadoraveiculo.model.Car_;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CarDAO {

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public Car save(Car car) {
        em.merge(car);
        return car;
    }

    public List<Car> findAll() {
        return em.createQuery("select c from Car c", Car.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Car carTemp = em.find(Car.class, id);

        em.remove(carTemp);
        em.flush();
    }

    public Car findById(Long carId) {
        return em.find(Car.class, carId);
    }

    public Car findByIdWithAccessories(Long carId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> q = cb.createQuery(Car.class);
        Root<Car> c = q.from(Car.class);
        c.fetch(Car_.ACCESSORIES, JoinType.LEFT);

        Predicate predicate = cb.equal(c.get(Car_.ID), carId);
        q.where(predicate);

        TypedQuery<Car> query = em.createQuery(q);
        return query.getSingleResult();
    }

    public List<Object[]> findDataGroupByCar() {
        List<Object[]> results = em.createQuery(
                "select c, count(r), max(r.totalValue), avg(r.totalValue) " +
                        "from Car c join c.rents r " +
                        "group by c " +
                        "having count(r) > 1").getResultList();

        return results;
    }
}

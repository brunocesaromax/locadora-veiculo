package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class RentDAO {

    @PersistenceContext
    private final EntityManager em;

    public Rent save(Rent rent) {
        em.merge(rent);
        return rent;
    }

    public List<Rent> findAll() {
        return em.createQuery("select r from Rent r", Rent.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Rent rentTemp = em.find(Rent.class, id);

        em.remove(rentTemp);
        em.flush();
    }

    public Rent findById(Long rentId) {
        return em.find(Rent.class, rentId);
    }

    public Long findAllByReturnDateInterval(Date start, Date end) {
        return em.createQuery("select count (r) " +
                "from Rent r " +
                "where r.returnDate between :start and :end", Long.class)
                .setParameter("start", start, TemporalType.TIMESTAMP)
                .setParameter("end", end, TemporalType.TIMESTAMP)
                .getSingleResult();
    }

    public BigDecimal sumTotalValue() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
        Root<Rent> rentRoot = criteriaQuery.from(Rent.class);

        criteriaQuery.select(builder.sum(rentRoot.get(Rent_.TOTAL_VALUE)));

        TypedQuery<BigDecimal> query = em.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    //Exemplo usando criteria
    public List<Rent> findByDeliveryDateAndCarModel(Date deliveryDate, CarModel carModel) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Rent> criteriaQuery = builder.createQuery(Rent.class);
        Root<Rent> root = criteriaQuery.from(Rent.class);
        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (deliveryDate != null) {
            /*Funciona como se fosse o deliveryDate between :startDate and :endDate*/
            ParameterExpression<Date> startDate = builder.parameter(Date.class, "startDate");
            ParameterExpression<Date> endDate = builder.parameter(Date.class, "endDate");
            predicates.add(builder.between(root.get(Rent_.DELIVERY_DATE), startDate, endDate));
        }

        if (carModel.getId() != null) {
            ParameterExpression<CarModel> carModelExpression = builder.parameter(CarModel.class, "carModelExpression");
            predicates.add(builder.equal(root.get(Rent_.CAR).get(Car_.CAR_MODEL), carModelExpression));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Rent> query = em.createQuery(criteriaQuery);

        if (deliveryDate != null) {
            Date startDeliveryDate = getStartDeliveryDate(deliveryDate);
            Date endDeliveryDate = getEndDeliveryDate(deliveryDate);

            query.setParameter("startDate", startDeliveryDate);
            query.setParameter("endDate", endDeliveryDate);
        }

        if (carModel.getId() != null) {
            query.setParameter("carModelExpression", carModel);
        }

        return query.getResultList();
    }

    public List<Rent> findByDeliveryDateAndCarModelCriteria(Date deliveryDate, CarModel carModel) {
        Session session = this.em.unwrap(Session.class); // Pegando a seção do hibernate a partir do entityManager
        Criteria criteria = session.createCriteria(Rent.class);

        if (deliveryDate != null) {
            Date startDeliveryDate = getStartDeliveryDate(deliveryDate);
            Date endDeliveryDate = getEndDeliveryDate(deliveryDate);

            criteria.add(Restrictions.between(Rent_.DELIVERY_DATE, startDeliveryDate, endDeliveryDate));
        }

        if (carModel != null && carModel.getId() != null) {
            criteria.createAlias("car", "c");
            criteria.add(Restrictions.eq("c.carModel", carModel));
        }

        return criteria.list();
    }

    public BigDecimal totalRentValueOfMonth(Month month) {
        Session session = this.em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Rent.class);

        criteria.setProjection(Projections.sum(Rent_.TOTAL_VALUE));

        // Adicionando função month que é implementada pela maioria dos bancos de dados
        criteria.add(Restrictions.sqlRestriction("month(request_date) = ?", month.getValue(), StandardBasicTypes.INTEGER));

        return (BigDecimal) criteria.uniqueResult();
    }

    private Date getStartDeliveryDate(Date deliveryDate) {
        Calendar startDeliveryDate = Calendar.getInstance();
        startDeliveryDate.setTime(deliveryDate);
        startDeliveryDate.set(Calendar.HOUR_OF_DAY, 0);
        startDeliveryDate.set(Calendar.MINUTE, 0);
        startDeliveryDate.set(Calendar.SECOND, 0);
        return startDeliveryDate.getTime();
    }

    private Date getEndDeliveryDate(Date deliveryDate) {
        Calendar endDeliveryDate = Calendar.getInstance();
        endDeliveryDate.setTime(deliveryDate);
        endDeliveryDate.set(Calendar.HOUR_OF_DAY, 23);
        endDeliveryDate.set(Calendar.MINUTE, 59);
        endDeliveryDate.set(Calendar.SECOND, 59);
        return endDeliveryDate.getTime();
    }
}

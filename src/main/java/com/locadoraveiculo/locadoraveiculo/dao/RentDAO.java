package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.model.Car_;
import com.locadoraveiculo.locadoraveiculo.model.Rent;
import com.locadoraveiculo.locadoraveiculo.model.Rent_;
import lombok.RequiredArgsConstructor;
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
      Calendar startDeliveryDate = Calendar.getInstance();
      startDeliveryDate.setTime(deliveryDate);
      startDeliveryDate.set(Calendar.HOUR_OF_DAY, 0);
      startDeliveryDate.set(Calendar.MINUTE, 0);
      startDeliveryDate.set(Calendar.SECOND, 0);

      Calendar endDeliveryDate = Calendar.getInstance();
      endDeliveryDate.setTime(deliveryDate);
      endDeliveryDate.set(Calendar.HOUR_OF_DAY, 23);
      endDeliveryDate.set(Calendar.MINUTE, 59);
      endDeliveryDate.set(Calendar.SECOND, 59);

      query.setParameter("startDate", startDeliveryDate.getTime());
      query.setParameter("endDate", endDeliveryDate.getTime());
    }

    if (carModel.getId() != null) {
      query.setParameter("carModelExpression", carModel);
    }

    return query.getResultList();
  }

  public BigDecimal sumTotalValue() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
        Root<Rent> rentRoot = criteriaQuery.from(Rent.class);

        criteriaQuery.select(builder.sum(rentRoot.get(Rent_.TOTAL_VALUE)));

        TypedQuery<BigDecimal> query = em.createQuery(criteriaQuery);
        return query.getSingleResult();
  }

}

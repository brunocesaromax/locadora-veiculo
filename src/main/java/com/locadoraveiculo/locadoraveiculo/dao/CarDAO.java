package com.locadoraveiculo.locadoraveiculo.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.locadoraveiculo.locadoraveiculo.info.CarInfo;
import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
import com.locadoraveiculo.locadoraveiculo.model.Car;
import com.locadoraveiculo.locadoraveiculo.model.Car_;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
//        return em.createQuery("select c from Car c", Car.class).getResultList();
    return em.createNamedQuery("Car.findAll", Car.class).getResultList();
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

  public List<RentCarInfo> findDataGroupByCar() {
    List<RentCarInfo> results = em.createQuery(
        "select NEW com.locadoraveiculo.locadoraveiculo.info.RentCarInfo (c, count(r), max(r.totalValue), avg(r.totalValue)) " +
            "from Car c join c.rents r " +
            "group by c " +
            "having count(r) > 1", RentCarInfo.class).getResultList();

    return results;
  }

  public Car findByPlate(String plate) {
    return em.createNamedQuery("Car.findByPlate", Car.class)
        .setParameter("plate", plate)
        .getSingleResult();
  }

  public List<Car> findWithPagination(int first, int pageSize) {
    return em.createNamedQuery("Car.findAll", Car.class)
        .setFirstResult(first)
        .setMaxResults(pageSize)
        .getResultList();
    /*Consulta gerada vai ser do tipo:
     * select c from Car c limit first, pageSize*/
  }

  public Long findTotalCars() {
    return em.createQuery("select count(c) from Car c", Long.class).getSingleResult();
  }

  public List<String> findAllPlates() {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
    Root<Car> carRoot = criteriaQuery.from(Car.class);

    criteriaQuery.select(carRoot.get(Car_.PLATE));

    TypedQuery<String> query = em.createQuery(criteriaQuery);

    return query.getResultList();
  }

  public List<Object[]> complexResult() {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
    Root<Car> carRoot = criteriaQuery.from(Car.class);

    criteriaQuery.multiselect(carRoot.get(Car_.PLATE), carRoot.get(Car_.dailyValue));

    TypedQuery<Object[]> query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public List<ObjectNode> complexResultTuple() {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();
    Root<Car> carRoot = criteriaQuery.from(Car.class);

    criteriaQuery.multiselect(carRoot.get(Car_.PLATE).alias("plate")
        , carRoot.get(Car_.dailyValue).alias("dailyValue"));

    TypedQuery<Tuple> query = em.createQuery(criteriaQuery);
    List<Tuple> results = query.getResultList();
    List<ObjectNode> jacksonJsonObject = _toJson(results);

    return jacksonJsonObject;
  }

  public List<CarInfo> complexResultConstructor() {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<CarInfo> criteriaQuery = builder.createQuery(CarInfo.class);
    Root<Car> carRoot = criteriaQuery.from(Car.class);

    criteriaQuery.select(builder.construct(CarInfo.class,
        carRoot.get(Car_.PLATE),
        carRoot.get(Car_.DAILY_VALUE)));

    TypedQuery<CarInfo> query = em.createQuery(criteriaQuery);

    return query.getResultList();
  }

  private List<ObjectNode> _toJson(List<Tuple> results) {
    List<ObjectNode> json = new ArrayList<>();

    ObjectMapper mapper = new ObjectMapper();

    for (Tuple t : results) {
      List<TupleElement<?>> cols = t.getElements();
      ObjectNode one = mapper.createObjectNode();

      cols.forEach(col -> one.put(col.getAlias(), t.get(col.getAlias()).toString()));

      json.add(one);
    }

    return json;
  }

}

package com.locadoraveiculo.locadoraveiculo.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.locadoraveiculo.locadoraveiculo.info.CarInfo;
import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
import com.locadoraveiculo.locadoraveiculo.model.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
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
        /*Só retorna os dados corretamente com persist, todo:Estudar novamente o que acontece com o merge*/
        em.persist(car);
        return car;
    }

    public List<Car> findAll() {
        //Find All com criteria
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = builder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        Join<Car, CarModel> carModelJoin = (Join) carRoot.fetch(Car_.CAR_MODEL);

        criteriaQuery.select(carRoot);
        TypedQuery<Car> query = em.createQuery(criteriaQuery);
        List<Car> cars = query.getResultList();

        /*Ao buscar carro de id = 12, não é feita outra consulta, pois está sendo
         * utilizado o cache de primeiro nível, lista já buscada, portanto pegará o
         * carro de id = 12 dessa lista.*/
        Car car = em.find(Car.class, 12L);
        System.out.println(car.getId());

        return cars;
//        return em.createQuery("select c from Car c", Car.class).getResultList();
//        return em.createNamedQuery("Car.findAll", Car.class).getResultList();
    }

    /*Consulta apenas para aprendizado, porém sempre dar preferência para JPQL ou criteriaQuery*/
    public List<Car> findAllWithNativeQuery() {
        Query query = em.createNativeQuery(" select * from car", Car.class);
        return query.getResultList();
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

    public Car removeFirstRentOfCar(Long carId) {
        Car car = em.find(Car.class, carId);
        // A partir de agora o primeiro aluguel removido ficará orfão e também será removido do BD
        // Desde que o objeto pai esteja anota com o Cascade Persist e orphanRemoval = true
        car.getRents().remove(0);
        em.persist(car);
        return car;
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

    public List<Car> findAllByColor(String color) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = builder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        Predicate predicate = builder.equal(builder.upper(carRoot.get(Car_.COLOR)), color.toUpperCase());

        criteriaQuery.select(carRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Car> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Car> findAllOrderedByDailyValue() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = builder.createQuery(Car.class);

        Root<Car> carRoot = criteriaQuery.from(Car.class);
        Order order = builder.desc(carRoot.get(Car_.DAILY_VALUE));

        criteriaQuery.select(carRoot);
        criteriaQuery.orderBy(order);

        TypedQuery<Car> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public Double findDailyValueCarsAVG() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Double> criteriaQuery = builder.createQuery(Double.class);

        Root<Car> carRoot = criteriaQuery.from(Car.class);
        criteriaQuery.select(builder.avg(carRoot.get(Car_.DAILY_VALUE)));

        TypedQuery<Double> query = em.createQuery(criteriaQuery);
        Double avgResult = query.getSingleResult();

        return avgResult;
    }

    public List<Car> findAllWithDailyValueGreaterOrEqualAverage() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = builder.createQuery(Car.class);
        Subquery<Double> subQuery = criteriaQuery.subquery(Double.class);

        Root<Car> carRoot = criteriaQuery.from(Car.class);
        Root<Car> subRoot = subQuery.from(Car.class);

        subQuery.select(builder.avg(subRoot.get(Car_.DAILY_VALUE)));

        criteriaQuery.select(carRoot);
        criteriaQuery.where(builder.greaterThanOrEqualTo(carRoot.get(Car_.DAILY_VALUE), subQuery));

        TypedQuery<Car> query = em.createQuery(criteriaQuery);
        List<Car> cars = query.getResultList();

        return cars;
    }

    /*select * from car where id not in (select car_id from rent where car_id is not null)*/
    public List<Car> findAllCarNeverRented() {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Car.class);

        //Para trazer acessórios dos carro e necessário dar o fetch na coleção desejada
        criteria.setFetchMode(Car_.ACCESSORIES, FetchMode.JOIN);

        DetachedCriteria criteriaRent = DetachedCriteria.forClass(Rent.class);
        criteriaRent.setProjection(Projections.property(Rent_.CAR));
        criteriaRent.add(Restrictions.isNotNull(Rent_.CAR));

        criteria.add(Property.forName(Car_.ID).notIn(criteriaRent));
        return criteria.list();
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

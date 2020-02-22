package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CarDAO {

    @PersistenceContext
    private final EntityManager em;

    public Car save(Car car) {
        em.merge(car);
        return car;
    }

    public List<Car> findAll() {
        return em.createQuery("select c from Car c").getResultList();
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
}

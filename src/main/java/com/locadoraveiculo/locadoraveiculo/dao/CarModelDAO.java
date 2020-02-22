package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.model.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CarModelDAO {

    @PersistenceContext
    private final EntityManager em;

    public CarModel save(CarModel carModel) {
        em.merge(carModel);
        return carModel;
    }

    public List<CarModel> findAll() {
        return em.createQuery("select cm from CarModel cm").getResultList();
    }

    @Transactional
    public void delete(Long id) {
        CarModel carModelTemp = em.find(CarModel.class, id);

        em.remove(carModelTemp);
        em.flush();
    }

    public CarModel findById(Long carModelId) {
        return em.find(CarModel.class, carModelId);
    }
}

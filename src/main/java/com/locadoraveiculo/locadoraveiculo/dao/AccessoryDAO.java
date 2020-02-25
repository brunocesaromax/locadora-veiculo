package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Accessory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AccessoryDAO {

    @PersistenceContext
    private final EntityManager em;

    public Accessory save(Accessory accessory) {
        em.merge(accessory);
        return accessory;
    }

    public List<Accessory> findAll() {
        return em.createQuery("select ac from Accessory ac", Accessory.class).getResultList();
    }

    public List<Accessory> findAllByCarModel(String modelCar) {
        return em.createQuery("select ac from Car c join c.accessories ac where c.carModel.description = :modelCar", Accessory.class)
                .setParameter("modelCar", modelCar)
                .getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Accessory accessoryTemp = em.find(Accessory.class, id);

        em.remove(accessoryTemp);
        em.flush();
    }

    public Accessory findById(Long accessoryId) {
        return em.find(Accessory.class, accessoryId);
    }
}

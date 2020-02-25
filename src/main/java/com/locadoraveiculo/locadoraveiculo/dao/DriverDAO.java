package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
}

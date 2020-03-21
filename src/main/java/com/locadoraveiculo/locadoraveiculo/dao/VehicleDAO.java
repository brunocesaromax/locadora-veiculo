package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Vehicle;
import com.locadoraveiculo.locadoraveiculo.model.VehicleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Repository
public class VehicleDAO {

    @PersistenceContext
    private final EntityManager em;

    public Vehicle save(Vehicle vehicle) {
        em.persist(vehicle);
        return vehicle;
    }

    public Vehicle findById(VehicleId id) {
        return em.find(Vehicle.class, id);
    }

    public String findDecriptionById(VehicleId id) {
         Vehicle vehicle = em.find(Vehicle.class, id);
         return vehicle.getDescription();
    }
}

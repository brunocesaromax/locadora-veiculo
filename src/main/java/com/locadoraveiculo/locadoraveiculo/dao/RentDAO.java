package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Car;
import com.locadoraveiculo.locadoraveiculo.model.Rent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
        return em.createQuery("select r from Rent r").getResultList();
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
}

package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Repository
public class SaleDAO {

    @PersistenceContext
    private final EntityManager em;

    public Sale save(Sale sale) {
        em.persist(sale);
        return sale;
    }

    @Transactional
    public void delete(Long id) {
        Sale saleTemp = em.find(Sale.class, id);
        em.remove(saleTemp);
        em.flush();
    }
}

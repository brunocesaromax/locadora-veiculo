package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Repository
public class ProducerDAO {

    @PersistenceContext
    private final EntityManager em;

    public Producer save(Producer producer) {
        em.persist(producer);
        return producer;
    }

}

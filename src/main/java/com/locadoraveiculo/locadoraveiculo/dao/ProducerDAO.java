package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProducerDAO {

    @PersistenceContext
    private final EntityManager em;

    public Producer save(Producer producer) {
        em.persist(producer);
        return producer;
    }

    public List<Producer> findAll() {
        return em.createQuery("select p from Producer p").getResultList();
    }

    public void delete(Long id) {
        Producer producerTemp = em.find(Producer.class, id);

        em.remove(producerTemp);
        em.flush();
    }
}

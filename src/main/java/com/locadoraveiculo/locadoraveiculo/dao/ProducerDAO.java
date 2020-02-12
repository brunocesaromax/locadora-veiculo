package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProducerDAO {

    @PersistenceContext
    private final EntityManager em;

    public Producer save(Producer producer) {
        em.merge(producer); // Salvar ou atualizar um novo objeto
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

    public Optional<Producer> findByName(String name) {
        Producer producerPersisted = null;

        try {
            producerPersisted = em.createQuery("select p from Producer p where p.name = :name", Producer.class)
                    .setParameter("name", name).getSingleResult();
        }catch (NoResultException exception){}
        return Optional.ofNullable(producerPersisted);
    }

    public Producer findById(Long producerId) {
        return em.find(Producer.class, producerId);
    }
}

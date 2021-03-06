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

    /*No CDI usaria o @Inject*/
    @PersistenceContext
    private final EntityManager em;

    public Producer save(Producer producer) {
        em.merge(producer); // Salvar ou atualizar um novo objeto
        return producer;
    }

    public List<Producer> findAll() {
        return em.createQuery("select p from Producer p", Producer.class).getResultList();
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
        } catch (NoResultException exception) {
        }
        return Optional.ofNullable(producerPersisted);
    }

    public Producer findById(Long producerId) {
        return em.find(Producer.class, producerId);
    }

    public List<String> findAllNames() {
        return em.createQuery("select p.name from Producer p", String.class).getResultList();
    }

    public List<Producer> pagination(int first, int pageSize) {
        return em.createNamedQuery("Producer.findAll", Producer.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }
}

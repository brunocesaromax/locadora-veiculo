package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Repository
public class CustomerDAO {

    @PersistenceContext
    private final EntityManager em;

    public Customer save(Customer customer) {
        em.persist(customer);
        return customer;
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }
}

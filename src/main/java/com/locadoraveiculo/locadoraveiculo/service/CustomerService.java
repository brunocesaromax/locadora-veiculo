package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Customer;

public interface CustomerService {
    Customer save(Customer customer);

    Customer findById(Long id);
}

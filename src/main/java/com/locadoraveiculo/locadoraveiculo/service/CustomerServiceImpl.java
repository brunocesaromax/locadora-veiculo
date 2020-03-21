package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.CustomerDAO;
import com.locadoraveiculo.locadoraveiculo.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return customerDAO.save(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findById(Long id) {
        return customerDAO.findById(id);
    }
}

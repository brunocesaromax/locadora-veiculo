package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Rent;

import java.util.List;

public interface RentService {
    Rent save(Rent rent);

    List<Rent> findAll();

    void delete(Long id);

    Rent update(Long rentId, Rent rent);
}
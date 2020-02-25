package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Driver;

import java.util.List;

public interface DriverService {
    Driver save(Driver driver);

    List<Driver> findAll();

    void delete(Long id);

    Driver update(Long driverId, Driver driver);
}

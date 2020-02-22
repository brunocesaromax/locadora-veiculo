package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Car;

import java.util.List;

public interface CarService {
    Car save(Car car);

    List<Car> findAll();

    void delete(Long id);

    Car update(Long accessoryId, Car car);
}

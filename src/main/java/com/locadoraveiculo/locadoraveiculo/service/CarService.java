package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
import com.locadoraveiculo.locadoraveiculo.model.Car;

import java.util.List;

public interface CarService {
    Car save(Car car);

    List<Car> findAll();

    void delete(Long id);

    Car update(Long accessoryId, Car car);

    Car findById(Long carId);

    Car findByIdWithAccessories(Long carId);

    List<RentCarInfo> findDataGroupByCar();

    Car findByPlate(String plate);

    List<Car> pagination(int first, int pageSize);

    List<String> findAllPlates();
}

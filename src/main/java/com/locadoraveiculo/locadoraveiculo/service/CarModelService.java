package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.exception.CarModelException;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;

import java.util.List;

public interface CarModelService {
    CarModel save(CarModel carModel) throws CarModelException;

    List<CarModel> findAll();

    void delete(Long id);

    CarModel update(Long carModelId, CarModel carModel) ;
}

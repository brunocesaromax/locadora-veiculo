package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.exception.CarModelException;
import com.locadoraveiculo.locadoraveiculo.filter.CarModelFilter;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;

import java.util.List;

public interface CarModelService {
    CarModel save(CarModel carModel) throws CarModelException;

    List<CarModel> findAll();

    void delete(Long id);

    CarModel update(Long carModelId, CarModel carModel) ;

    List<String> findAllProducerNames();

    List<CarModel> findAllByProducerName(String producerName);

    List<CarModel> findAllByFilter(CarModelFilter filter);
}

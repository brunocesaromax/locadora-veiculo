package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.model.Rent;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RentService {
    Rent save(Rent rent);

    List<Rent> findAll();

    void delete(Long id);

    Rent update(Long rentId, Rent rent);

    Long findAllByReturnDateInterval(Date start, Date end);

    List<Rent> findByDeliveryDateAndCarModel(Date deliveryDate, CarModel carModel);

    BigDecimal sumTotalValue();
}

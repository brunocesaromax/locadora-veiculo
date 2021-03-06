package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.model.Month;
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

    BigDecimal sumTotalValue();

    List<Rent> findByDeliveryDateAndCarModel(Date deliveryDate, CarModel carModel);

    List<Rent> findByDeliveryDateAndCarModelCriteria(Date deliveryDate, CarModel carModel);

    BigDecimal totalRentValueOfMonth(Month month);

    void updateTotalValueOfRents(BigDecimal value);

    void deleteRentsWithDeliveryDateBeforeBy(Date date);
}

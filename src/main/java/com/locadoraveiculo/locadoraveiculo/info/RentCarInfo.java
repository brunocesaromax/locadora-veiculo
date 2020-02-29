package com.locadoraveiculo.locadoraveiculo.info;

import com.locadoraveiculo.locadoraveiculo.model.Car;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RentCarInfo {
    private Car car;
    private Long totalRents;
    private BigDecimal maxValue;
    private BigDecimal mediumValue;

    public RentCarInfo(Car car, Long totalRents, Number maxValue, Number mediumValue) {
        this.car = car;
        this.totalRents = totalRents;
        this.maxValue = BigDecimal.valueOf(maxValue.doubleValue());
        this.mediumValue = BigDecimal.valueOf(mediumValue.doubleValue());
    }
}

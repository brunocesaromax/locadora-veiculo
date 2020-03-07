package com.locadoraveiculo.locadoraveiculo.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class CarInfo {
    private String plate;
    private BigDecimal value;
}

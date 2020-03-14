package com.locadoraveiculo.locadoraveiculo.info;

import com.locadoraveiculo.locadoraveiculo.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DriverInfo {
    private Driver driver;
    private Long numberOfRents;
}

package com.locadoraveiculo.locadoraveiculo.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class TotalRentsByCar implements Serializable {

    private String plate;
    private Long totalRents;
}

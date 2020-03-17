package com.locadoraveiculo.locadoraveiculo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tab_vehicle")
@EqualsAndHashCode
@Setter
@Getter
public class Vehicle {

    /*Id é uma chave composta*/
    @EmbeddedId
    private VehicleId id;

    private String producer;
    private String model;
}

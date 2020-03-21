package com.locadoraveiculo.locadoraveiculo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    /*Deixa o projeto mais bem orientado a objeto,
     * além de melhorar a performace evitando joins*/
    @Embedded
    private Owner owner;

    @Transient // Necessário quando criar um metódo ou atributo não persistível no BD
    public String getDescription() {
        return "Placa: " + getId().getPlate() + ". Fabricante: " +
                getProducer() + ". Modelo: " + getModel();
    }
}

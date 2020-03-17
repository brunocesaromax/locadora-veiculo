package com.locadoraveiculo.locadoraveiculo.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor // Necessário para o hibernate criar os objetos
@EqualsAndHashCode
@Embeddable //Classe pode ser embutida em outra classe
public class VehicleId implements Serializable {
    private String plate;
    private String city;
}

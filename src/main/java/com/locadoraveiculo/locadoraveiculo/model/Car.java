package com.locadoraveiculo.locadoraveiculo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String plate;

    @NotEmpty
    private String color;

    @NotEmpty
    private String chassis;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal dailyValue;

    @NotNull
    @ManyToOne
    private CarModel carModel;

    @ManyToMany
    private List<Accessory> accessories;

    @JsonManagedReference
    @OneToMany(mappedBy = "car") // Foi mapeado na classe Aluguel no atributo 'car'
    private List<Rent> rents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car producer = (Car) o;
        return Objects.equals(id, producer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

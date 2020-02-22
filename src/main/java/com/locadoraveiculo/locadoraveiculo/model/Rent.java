package com.locadoraveiculo.locadoraveiculo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Setter
@Getter
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalValue;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "car_id")
    @NotNull
    private Car car;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @Valid
    private InsurancePolicy insurancePolicy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rent)) return false;
        Rent producer = (Rent) o;
        return Objects.equals(id, producer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

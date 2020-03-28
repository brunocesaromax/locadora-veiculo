package com.locadoraveiculo.locadoraveiculo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "insurance_policy", schema = "locadoraveiculos")
@Setter
@Getter
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal deductibleAmount;

    @NotNull
    private Boolean protectionThirdParties;

    @NotNull
    private Boolean protectionNaturalCauses;

    @NotNull
    private Boolean protectionTheft;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InsurancePolicy)) return false;
        InsurancePolicy producer = (InsurancePolicy) o;
        return Objects.equals(id, producer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

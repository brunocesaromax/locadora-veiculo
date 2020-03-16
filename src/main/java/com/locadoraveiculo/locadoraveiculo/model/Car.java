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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
/*Os nomes de namedQueries devem ser únicos no sistema,
 * Duas entidades não podem compartilhar o mesmo namedQuery name.
 * NamedQueries são mais utilizadas quando se sabe que a consulta dificilmente
 * sofrerá alterações, ou seja, queries mais estáticas.*/
@NamedQueries({
        // Melhorando a consulta com inner join fetch para ser feito em apenas uma chamada no banco
        @NamedQuery(name = "Car.findAll", query = "select c from Car c inner join fetch c.carModel"),
        @NamedQuery(name = "Car.findByPlate", query = "select c from Car c where c.plate = :plate")
})
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
    /*Persistir modelo de carro ao salvar um novo carro*/
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private CarModel carModel;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Accessory> accessories;

    @JsonManagedReference
    // Foi mapeado na classe Aluguel no atributo 'car'
    // Ao remover um rent de Car e persistir novamente o rent que ficou orfão será removido
    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Rent> rents;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /*Metódos de callback*/
    @PrePersist
    @PreUpdate
    public void configureDatesToCreateOrUpdate() {
        this.lastModifiedDate = new Date();
        if (this.createDate == null)
            this.createDate = new Date();
    }

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

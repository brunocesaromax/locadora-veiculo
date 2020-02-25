package com.locadoraveiculo.locadoraveiculo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
//Uma tabela com um nova coluna descrevendo o tipo
//Forma bastante simples e perfomática
//Cuidado ao usar Heranças
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//Coluna que faz a discriminação
@DiscriminatorColumn(name = "PERSON_TYPE", discriminatorType = DiscriminatorType.STRING)
@Setter
@Getter
public abstract class Person { //Por si só não faz sentido

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateBirth;

    @NotEmpty
    private String documentIdentifier;//CPF
}

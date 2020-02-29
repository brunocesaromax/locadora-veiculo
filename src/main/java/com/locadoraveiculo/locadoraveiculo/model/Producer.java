package com.locadoraveiculo.locadoraveiculo.model;

import com.locadoraveiculo.locadoraveiculo.constraint.UniqueProducerName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Setter
@Getter
@UniqueProducerName
@NamedQuery(name = "Producer.findAll", query = "select p from Producer p")
public class Producer {

//    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO) // O Banco de dados escolhe o melhor para a ação de gerar o ID
////    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Banco de dados usando sequence (Oracle utiliza)
////    @GeneratedValue(strategy = GenerationType.TABLE) // Cria nova tabela no BD e utiliza as tuplas como id nas outras tabelas
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Banco de dados incrementa a primary key
////    @Column(name = "code") Nome na tabela do banco de dados
//    private Long id;

    /*@Id
    @TableGenerator(name = "producer_generator", table = "CODE_GENERATOR", pkColumnName = "ENTIDADE",
    valueColumnName = "ALLOCATION", allocationSize = 5)
    @GeneratedValue(generator = "producer_generator", strategy = GenerationType.TABLE)
    private Long id;*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    //O equals hash code padrão é o da Classe Object e é por região de memória
    // HashCode deixa as buscas por hash mais rápidas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producer)) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

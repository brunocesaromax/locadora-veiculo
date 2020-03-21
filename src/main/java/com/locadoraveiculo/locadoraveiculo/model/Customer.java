package com.locadoraveiculo.locadoraveiculo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_phones",
            joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "phone_number")
    private List<String> phones = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @CollectionTable(name = "customer_addresses",
            joinColumns = @JoinColumn(name = "customer_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street"))
    })
    private List<Address> addresses;
}

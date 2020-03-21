package com.locadoraveiculo.locadoraveiculo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class Owner {

    @Column(name = "name_owner")
    private String name;

    @Column(name = "phone_owner")
    private String phone;

    @Column(name = "email_owner")
    private String email;
}

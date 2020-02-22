package com.locadoraveiculo.locadoraveiculo;

import com.locadoraveiculo.locadoraveiculo.model.Producer;

public class ObjectEquals {

    public static void main(String[] args) {
        Producer p1 = new Producer();
        p1.setId(3L);

        Producer p2 = new Producer();
        p2.setId(3L);

        System.out.println("p1.equals(p2) ? "+p1.equals(p2));
    }
}

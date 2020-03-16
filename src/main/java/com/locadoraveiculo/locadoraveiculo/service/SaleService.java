package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Sale;

public interface SaleService {
    Sale save(Sale sale);

    void remove(Long id);
}

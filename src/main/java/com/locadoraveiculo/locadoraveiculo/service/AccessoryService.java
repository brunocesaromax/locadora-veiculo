package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Accessory;

import java.util.List;

public interface AccessoryService {
    Accessory save(Accessory accessory);

    List<Accessory> findAll();

    void delete(Long id);

    Accessory update(Long accessoryId, Accessory accessory);
}

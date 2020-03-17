package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Vehicle;
import com.locadoraveiculo.locadoraveiculo.model.VehicleId;

public interface VehicleService {
    Vehicle save(Vehicle vehicle);

    Vehicle findById(VehicleId id);
}

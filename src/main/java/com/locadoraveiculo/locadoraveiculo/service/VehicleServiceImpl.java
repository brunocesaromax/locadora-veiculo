package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.VehicleDAO;
import com.locadoraveiculo.locadoraveiculo.model.Vehicle;
import com.locadoraveiculo.locadoraveiculo.model.VehicleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private final VehicleDAO vehicleDAO;

    @Transactional
    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleDAO.save(vehicle);
    }

    @Override
    public Vehicle findById(VehicleId id) {
        return vehicleDAO.findById(id);
    }
}

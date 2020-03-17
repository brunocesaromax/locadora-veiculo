package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.Vehicle;
import com.locadoraveiculo.locadoraveiculo.model.VehicleId;
import com.locadoraveiculo.locadoraveiculo.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Validated
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> save(@Valid @RequestBody Vehicle vehicle){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.save(vehicle));
    }

    @GetMapping
    public ResponseEntity<Vehicle> findById(VehicleId vehicleId){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findById(vehicleId));
    }

}

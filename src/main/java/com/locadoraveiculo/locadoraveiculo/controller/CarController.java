package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.Car;
import com.locadoraveiculo.locadoraveiculo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<Car> save(@Valid @RequestBody Car car){
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(car));
    }

    @GetMapping
    public ResponseEntity<List<Car>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(carService.findByIdWithAccessories(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@PathVariable Long id, @Valid @RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.update(id, car));
    }
}

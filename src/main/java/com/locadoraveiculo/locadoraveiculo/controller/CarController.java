package com.locadoraveiculo.locadoraveiculo.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.locadoraveiculo.locadoraveiculo.info.CarInfo;
import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
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
  public ResponseEntity<Car> save(@Valid @RequestBody Car car) {
    return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(car));
  }

  @GetMapping
  public ResponseEntity<List<Car>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(carService.findAll());
  }

  @GetMapping(params = "groupByCar")
  public ResponseEntity<List<RentCarInfo>> findDataGroupByCar() {
    return ResponseEntity.status(HttpStatus.OK).body(carService.findDataGroupByCar());
  }

  @GetMapping("{id}")
  public ResponseEntity<Car> findById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(carService.findByIdWithAccessories(id));
  }

  @GetMapping(params = "plate")
  public ResponseEntity<Car> findById(@RequestParam String plate) {
    return ResponseEntity.status(HttpStatus.OK).body(carService.findByPlate(plate));
  }

  @GetMapping("plates")
  public ResponseEntity<List<String>> findAllPlates() {
    return ResponseEntity.status(HttpStatus.OK).body(carService.findAllPlates());
  }

  @GetMapping(params = "pagination")
  public ResponseEntity<List<Car>> pagination(@RequestParam int first,
                                              @RequestParam int pageSize) {
    return ResponseEntity.status(HttpStatus.OK).body(carService.pagination(first, pageSize));
  }

  @GetMapping("complex-result-simple")
  public ResponseEntity<List<Object[]>> complexResult() {
    return ResponseEntity.status(HttpStatus.OK).body(carService.complexResult());
  }

  @GetMapping("complex-result-tuple")
  public ResponseEntity<List<ObjectNode>> complexResultTuple() {
    return ResponseEntity.status(HttpStatus.OK).body(carService.complexResultTuple());
  }

  /*Melhor forma a se fazer projeções já que já é orientada a objeto*/
  @GetMapping("complex-result-constructor")
  public ResponseEntity<List<CarInfo>> complexResultConstructor() {
    return ResponseEntity.status(HttpStatus.OK).body(carService.complexResultConstructor());
  }

  @GetMapping(params = "color")
  public ResponseEntity<List<Car>> findAllByColor(@RequestParam String color) {
    return ResponseEntity.status(HttpStatus.OK).body(carService.findAllByColor(color));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    carService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Car> update(@PathVariable Long id, @Valid @RequestBody Car car) {
    return ResponseEntity.status(HttpStatus.OK).body(carService.update(id, car));
  }
}

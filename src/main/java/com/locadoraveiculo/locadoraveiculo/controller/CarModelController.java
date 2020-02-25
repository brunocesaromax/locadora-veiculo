package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.filter.CarModelFilter;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.service.CarModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car-models")
@RequiredArgsConstructor
@Validated
public class CarModelController {

    private final CarModelService carModelService;

    @PostMapping
    public ResponseEntity<CarModel> save(@Valid @RequestBody CarModel carModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(carModelService.save(carModel));
    }

    @PostMapping(params = "filter")
    public ResponseEntity<List<CarModel>> findAllByFilter(@RequestBody CarModelFilter filter){
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.findAllByFilter(filter));
    }

    @GetMapping
    public ResponseEntity<List<CarModel>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.findAll());
    }

    @GetMapping("producers/names")
    public ResponseEntity<List<String>> findAllProducerNames(){
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.findAllProducerNames());
    }

    @GetMapping(params = "producerName")
    public ResponseEntity<List<CarModel>> findAllByProducerName(@RequestParam String producerName){
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.findAllByProducerName(producerName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        carModelService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarModel> update(@PathVariable Long id, @Valid @RequestBody CarModel carModel) {
        return ResponseEntity.status(HttpStatus.OK).body(carModelService.update(id, carModel));
    }
}

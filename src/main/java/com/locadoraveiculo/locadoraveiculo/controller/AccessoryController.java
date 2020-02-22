package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.Accessory;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.service.AccessoryService;
import com.locadoraveiculo.locadoraveiculo.service.CarModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
@Validated
public class AccessoryController {

    private final AccessoryService accessoryService;

    @PostMapping
    public ResponseEntity<Accessory> save(@Valid @RequestBody Accessory accessory){
        return ResponseEntity.status(HttpStatus.CREATED).body(accessoryService.save(accessory));
    }

    @GetMapping
    public ResponseEntity<List<Accessory>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(accessoryService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        accessoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accessory> update(@PathVariable Long id, @Valid @RequestBody Accessory accessory) {
        return ResponseEntity.status(HttpStatus.OK).body(accessoryService.update(id, accessory));
    }
}

package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.config.SwaggerConfig;
import com.locadoraveiculo.locadoraveiculo.model.Accessory;
import com.locadoraveiculo.locadoraveiculo.service.AccessoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
@Api(tags = SwaggerConfig.VEHICLE_RENTAL)
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

    @GetMapping(params = "carModelDescription")
    public ResponseEntity<List<Accessory>> findAllByCarModelDescription(@RequestParam String carModelDescription){
        return ResponseEntity.status(HttpStatus.OK).body(accessoryService.findAllByCarModelDescription(carModelDescription));
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

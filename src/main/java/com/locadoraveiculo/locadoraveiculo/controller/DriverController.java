package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.config.SwaggerConfig;
import com.locadoraveiculo.locadoraveiculo.info.DriverInfo;
import com.locadoraveiculo.locadoraveiculo.model.Driver;
import com.locadoraveiculo.locadoraveiculo.service.DriverService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Api(tags = SwaggerConfig.DRIVER_API_TAG)
@Validated
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<Driver> save(@Valid @RequestBody Driver driver){
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.save(driver));
    }

    @GetMapping
    public ResponseEntity<List<Driver>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(driverService.findAll());
    }

    @GetMapping(value = "top-five")
    public ResponseEntity<List<DriverInfo>> findTopFive(){
        return ResponseEntity.status(HttpStatus.OK).body(driverService.findTopFive());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        driverService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> update(@PathVariable Long id, @Valid @RequestBody Driver driver) {
        return ResponseEntity.status(HttpStatus.OK).body(driverService.update(id, driver));
    }
}

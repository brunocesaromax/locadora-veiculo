package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.config.SwaggerConfig;
import com.locadoraveiculo.locadoraveiculo.model.Sale;
import com.locadoraveiculo.locadoraveiculo.service.SaleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@Api(tags = SwaggerConfig.VEHICLE_RENTAL)
@Validated
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<Sale> save(@Valid @RequestBody Sale sale){
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(sale));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        saleService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

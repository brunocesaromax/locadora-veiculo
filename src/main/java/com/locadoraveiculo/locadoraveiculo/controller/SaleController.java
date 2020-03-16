package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.Sale;
import com.locadoraveiculo.locadoraveiculo.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
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

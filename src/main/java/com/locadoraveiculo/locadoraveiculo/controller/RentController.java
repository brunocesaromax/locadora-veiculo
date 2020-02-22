package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.Rent;
import com.locadoraveiculo.locadoraveiculo.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@Validated
public class RentController {

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<Rent> save(@Valid @RequestBody Rent rent){
        return ResponseEntity.status(HttpStatus.CREATED).body(rentService.save(rent));
    }

    @GetMapping
    public ResponseEntity<List<Rent>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(rentService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        rentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rent> update(@PathVariable Long id, @Valid @RequestBody Rent rent) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.update(id, rent));
    }
}

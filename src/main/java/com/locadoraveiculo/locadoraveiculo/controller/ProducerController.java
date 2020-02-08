package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.Producer;
import com.locadoraveiculo.locadoraveiculo.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping
    public ResponseEntity<Producer> save(@Valid @RequestBody Producer producer){
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.save(producer));
    }

    @GetMapping
    public ResponseEntity<List<Producer>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(producerService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        producerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

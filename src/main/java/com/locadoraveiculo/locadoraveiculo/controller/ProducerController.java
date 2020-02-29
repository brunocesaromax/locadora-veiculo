package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.Producer;
import com.locadoraveiculo.locadoraveiculo.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
@Validated
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping
    public ResponseEntity<Producer> save(@Valid @RequestBody Producer producer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(producerService.save(producer));
    }

    @GetMapping
    public ResponseEntity<List<Producer>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(producerService.findAll());
    }

    @GetMapping("names")
    public ResponseEntity<List<String>> findAllNames() {
        return ResponseEntity.status(HttpStatus.OK).body(producerService.findAllNames());
    }

    @GetMapping(params = "pagination")
    public ResponseEntity<List<Producer>> pagination(@RequestParam int first,
                                                     @RequestParam int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(producerService.pagination(first, pageSize));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        producerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producer> update(@PathVariable Long id, @Valid @RequestBody Producer producer) {
        return ResponseEntity.status(HttpStatus.OK).body(producerService.update(id, producer));
    }
}

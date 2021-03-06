package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.model.Producer;

import java.util.List;

public interface ProducerService {
    Producer save(Producer producer);

    List<Producer> findAll();

    void delete(Long id);

    Producer update(Long producerId, Producer producer) ;

    List<String> findAllNames();

    List<Producer> pagination(int first, int pageSize);
}

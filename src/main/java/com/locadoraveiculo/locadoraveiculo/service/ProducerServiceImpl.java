package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.ProducerDAO;
import com.locadoraveiculo.locadoraveiculo.model.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService{

    private final ProducerDAO producerDAO;

    @Transactional
    @Override
    public Producer save(Producer producer) {
        return producerDAO.save(producer);
    }
}

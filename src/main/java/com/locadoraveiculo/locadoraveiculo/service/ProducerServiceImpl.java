package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.ProducerDAO;
import com.locadoraveiculo.locadoraveiculo.model.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService{

    private final ProducerDAO producerDAO;

    @Transactional
    @Override
    public Producer save(Producer producer) {
        return producerDAO.save(producer);
    }

    @Override
    public List<Producer> findAll() {
        return producerDAO.findAll();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        producerDAO.delete(id);
    }
}

package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.ProducerDAO;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.model.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    @Override
    public Producer update(Long producerId, Producer producer) {
        Producer producerOld = producerDAO.findById(producerId);

        if (producerOld == null){
            throw new NotFoundException();
        }else{
            BeanUtils.copyProperties(producer, producerOld, "id");
            return producerDAO.save(producerOld);
        }
    }
}

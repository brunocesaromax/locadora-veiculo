package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.CarModelDAO;
import com.locadoraveiculo.locadoraveiculo.exception.CarModelException;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.filter.CarModelFilter;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarModelServiceImpl implements CarModelService{

    private final CarModelDAO carModelDAO;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public CarModel save(CarModel carModel) throws CarModelException {
        if (StringUtils.isEmpty(carModel.getDescription())){
            throw new CarModelException(messageSource.getMessage("CarModel.required.description", null, LocaleContextHolder.getLocale()));
        }

        if (carModel.getProducer() == null){
            throw new CarModelException(messageSource.getMessage("CarModel.required.producer", null, LocaleContextHolder.getLocale()));
        }

        return carModelDAO.save(carModel);
    }

    @Override
    public List<CarModel> findAll() {
        return carModelDAO.findAll();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        carModelDAO.delete(id);
    }

    @Transactional
    @Override
    public CarModel update(Long carModelId, CarModel carModel) {
        CarModel carModelOld = carModelDAO.findById(carModelId);

        if (carModelOld == null){
            throw new NotFoundException();
        }else{
            BeanUtils.copyProperties(carModel, carModelOld, "id", "producer");
            return carModelDAO.save(carModelOld);
        }
    }

    @Override
    public List<String> findAllProducerNames() {
        return carModelDAO.findProducerNames();
    }

    @Override
    public List<CarModel> findAllByProducerName(String producerName) {
        return carModelDAO.findAllByProducerName(producerName);
    }

    @Override
    public List<CarModel> findAllByFilter(CarModelFilter filter) {
        return carModelDAO.findAllByFilter(filter);
    }
}

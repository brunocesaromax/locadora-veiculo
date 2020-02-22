package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.CarModelDAO;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import lombok.RequiredArgsConstructor;
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
        return null;
    }

    @Transactional
    @Override
    public void delete(Long id) {

    }

    @Transactional
    @Override
    public CarModel update(Long carModelId, CarModel carModel) {
        return null;
    }
}

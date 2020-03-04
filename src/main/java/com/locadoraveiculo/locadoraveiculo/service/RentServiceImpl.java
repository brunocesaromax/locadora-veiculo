package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.RentDAO;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.model.Rent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService{

    private final RentDAO rentDAO;

    @Transactional
    @Override
    public Rent save(Rent rent) {
        return rentDAO.save(rent);
    }

    @Override
    public List<Rent> findAll() {
        return rentDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        rentDAO.delete(id);
    }

    @Override
    public Rent update(Long rentId, Rent rent) {
        Rent rentOld = rentDAO.findById(rentId);

        if (rentOld == null){
            throw new NotFoundException();
        }else{
            BeanUtils.copyProperties(rent, rentOld, "id");
            return rentDAO.save(rentOld);
        }
    }

    @Override
    public Long findAllByReturnDateInterval(Date start, Date end) {
        return rentDAO.findAllByReturnDateInterval(start, end);
    }

    @Override
    public List<Rent> findByDeliveryDateAndCarModel(Date deliveryDate, CarModel carModel) {
        return rentDAO.findByDeliveryDateAndCarModel(deliveryDate, carModel);
    }
}

package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.DriverDAO;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.model.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService{

    private final DriverDAO driverDAO;

    @Transactional
    @Override
    public Driver save(Driver driver) {
        return driverDAO.save(driver);
    }

    @Override
    public List<Driver> findAll() {
        return driverDAO.findAll();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        driverDAO.delete(id);
    }

    @Transactional
    @Override
    public Driver update(Long driverId, Driver driver) {
        Driver driverOld = driverDAO.findById(driverId);

        if (driverOld == null){
            throw new NotFoundException();
        }else{
            BeanUtils.copyProperties(driver, driverOld, "id");
            return driverDAO.save(driverOld);
        }
    }
}

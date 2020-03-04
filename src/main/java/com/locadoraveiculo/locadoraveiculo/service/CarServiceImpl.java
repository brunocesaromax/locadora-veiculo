package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.CarDAO;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
import com.locadoraveiculo.locadoraveiculo.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarDAO carDAO;

    @Transactional
    @Override
    public Car save(Car car) {
        return carDAO.save(car);
    }

    @Override
    public List<Car> findAll() {
        return carDAO.findAll();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        carDAO.delete(id);
    }

    @Transactional
    @Override
    public Car update(Long accessoryId, Car car) {
        Car carOld = carDAO.findById(accessoryId);

        if (carOld == null) {
            throw new NotFoundException();
        } else {
            BeanUtils.copyProperties(car, carOld, "id");
            return carDAO.save(carOld);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Car findById(Long carId) {
        return carDAO.findById(carId);
    }

    @Transactional(readOnly = true)
    public Car findByIdWithAccessories(Long carId) {
        return carDAO.findByIdWithAccessories(carId);
    }

    @Override
    public List<RentCarInfo> findDataGroupByCar() {
        return carDAO.findDataGroupByCar();
    }

    @Override
    public Car findByPlate(String plate) {
        return carDAO.findByPlate(plate);
    }

    @Override
    public List<Car> pagination(int first, int pageSize) {
        return carDAO.findWithPagination(first, pageSize);
    }

  @Override
  public List<String> findAllPlates() {
    return carDAO.findAllPlates();
  }
}

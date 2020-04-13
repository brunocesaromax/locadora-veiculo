package com.locadoraveiculo.locadoraveiculo.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.locadoraveiculo.locadoraveiculo.dao.CarDAO;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.info.CarInfo;
import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
import com.locadoraveiculo.locadoraveiculo.info.TotalRentsByCar;
import com.locadoraveiculo.locadoraveiculo.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Override
    public List<Object[]> complexResult() {
        return carDAO.complexResult();
    }

    @Override
    public List<ObjectNode> complexResultTuple() {
        return carDAO.complexResultTuple();
    }

    @Override
    public List<CarInfo> complexResultConstructor() {
        return carDAO.complexResultConstructor();
    }

    @Override
    public List<Car> findAllByColor(String color) {
        return carDAO.findAllByColor(color);
    }

    @Override
    public List<Car> findAllOrderedByDailyValue() {
        return carDAO.findAllOrderedByDailyValue();
    }

    @Override
    public Double findDailyValueCarsAVG() {
        return carDAO.findDailyValueCarsAVG();
    }

    @Override
    public List<Car> findAllWithDailyValueGreaterOrEqualAverage() {
        return carDAO.findAllWithDailyValueGreaterOrEqualAverage();
    }

    @Transactional
    @Override
    public Car removeFirstRentOfCar(Long carId) {
        return carDAO.removeFirstRentOfCar(carId);
    }

    @Override
    public void uploadImage(Long id, MultipartFile file) throws IOException {
        Car car = findById(id);
        car.setImage(file.getBytes());
        carDAO.save(car);
    }

    @Override
    public List<Car> findAllWithNativeQuery() {
        return carDAO.findAllWithNativeQuery();
    }

    @Override
    public List<Car> findAllCarNeverRented() {
        return carDAO.findAllCarNeverRented();
    }

    @Override
    public List<TotalRentsByCar> findTotalRentsByCar() {
        return carDAO.findTotalRentsByCar();
    }
}

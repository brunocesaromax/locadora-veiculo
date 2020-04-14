package com.locadoraveiculo.locadoraveiculo.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.locadoraveiculo.locadoraveiculo.info.CarInfo;
import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
import com.locadoraveiculo.locadoraveiculo.info.TotalRentsByCar;
import com.locadoraveiculo.locadoraveiculo.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    Car save(Car car);

    List<Car> findAll();

    void delete(Long id);

    Car update(Long accessoryId, Car car);

    Car findById(Long carId);

    Car findByIdWithAccessories(Long carId);

    List<RentCarInfo> findDataGroupByCar();

    Car findByPlate(String plate);

    List<Car> pagination(int first, int pageSize);

    List<String> findAllPlates();

    List<Object[]> complexResult();

    List<ObjectNode> complexResultTuple();

    List<CarInfo> complexResultConstructor();

    List<Car> findAllByColor(String color);

    List<Car> findAllOrderedByDailyValue();

    Double findDailyValueCarsAVG();

    List<Car> findAllWithDailyValueGreaterOrEqualAverage();

    Car removeFirstRentOfCar(Long carId);

    void uploadImage(Long id, MultipartFile file) throws IOException;

    List<Car> findAllWithNativeQuery();

    List<Car> findAllCarNeverRented();

    List<TotalRentsByCar> findTotalRentsByCar();

    List<TotalRentsByCar> findTotalRentsByCarWithSqlResultSetMapping();
}

package com.locadoraveiculo.locadoraveiculo.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.locadoraveiculo.locadoraveiculo.config.SwaggerConfig;
import com.locadoraveiculo.locadoraveiculo.info.CarInfo;
import com.locadoraveiculo.locadoraveiculo.info.RentCarInfo;
import com.locadoraveiculo.locadoraveiculo.info.TotalRentsByCar;
import com.locadoraveiculo.locadoraveiculo.model.Car;
import com.locadoraveiculo.locadoraveiculo.service.CarService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Api(tags = SwaggerConfig.VEHICLE_RENTAL)
@Validated
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<Car> save(@Valid @RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(car));
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<List<Car>> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        carService.uploadImage(id, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*Para retornar imagem em dados brutos usar:
    * produces = MediaType.APPLICATION_OCTET_STREAM_VALUE*/
    @GetMapping(value = "/{id}/image-car",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageWithMediaType(@PathVariable Long id) {
        Car car = carService.findById(id);
        return car.getImage();
    }

    @GetMapping
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAll());
    }

    @GetMapping(params = "nativeQuery")
    public ResponseEntity<List<Car>> findAllWithNativeQuery() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAllWithNativeQuery());
    }

    @GetMapping(params = "groupByCar")
    public ResponseEntity<List<RentCarInfo>> findDataGroupByCar() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findDataGroupByCar());
    }

    @GetMapping("{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findByIdWithAccessories(id));
    }

    @GetMapping(params = "plate")
    public ResponseEntity<Car> findById(@RequestParam String plate) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findByPlate(plate));
    }

    @GetMapping("plates")
    public ResponseEntity<List<String>> findAllPlates() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAllPlates());
    }

    @GetMapping(params = "pagination")
    public ResponseEntity<List<Car>> pagination(@RequestParam int first,
                                                @RequestParam int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.pagination(first, pageSize));
    }

    @GetMapping("complex-result-simple")
    public ResponseEntity<List<Object[]>> complexResult() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.complexResult());
    }

    @GetMapping("complex-result-tuple")
    public ResponseEntity<List<ObjectNode>> complexResultTuple() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.complexResultTuple());
    }

    /*Melhor forma a se fazer projeções já que já é orientada a objeto*/
    @GetMapping("complex-result-constructor")
    public ResponseEntity<List<CarInfo>> complexResultConstructor() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.complexResultConstructor());
    }

    @GetMapping(params = "color")
    public ResponseEntity<List<Car>> findAllByColor(@RequestParam String color) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAllByColor(color));
    }

    @GetMapping(params = "orderly-by-daily-value")
    public ResponseEntity<List<Car>> findAllOrderedByDailyValue() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAllOrderedByDailyValue());
    }

    @GetMapping(value = "find-daily-value-cars-avg")
    public ResponseEntity<Double> findDailyValueCarsAVG() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findDailyValueCarsAVG());
    }

    @GetMapping(value = "find-cars-with-daily-value-greater-avg")
    public ResponseEntity<List<Car>> findAllWithDailyValueGreaterOrEqualAverage() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAllWithDailyValueGreaterOrEqualAverage());
    }

    @GetMapping("/never-rented")
    public ResponseEntity<List<Car>> findAllCarNeverRented() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAllCarNeverRented());
    }

    @GetMapping("/total-rents-by-car")
    public ResponseEntity<List<TotalRentsByCar>> findTotalRentsByCar() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findTotalRentsByCar());
    }

    @GetMapping("/total-rents-by-car-jpa2.1")
    public ResponseEntity<List<TotalRentsByCar>> findTotalRentsByCarWithSqlResultSetMapping() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findTotalRentsByCarWithSqlResultSetMapping());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@PathVariable Long id, @Valid @RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.update(id, car));
    }

    @PutMapping("/{id}/change-to-color-red")
    public ResponseEntity<Car> updateWithColorRed(@PathVariable Long id) {
        carService.updateCarWithColorNotEqualsRed(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/remove-first-rent")
    public ResponseEntity<Car> update(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.removeFirstRentOfCar(id));
    }
}

package com.locadoraveiculo.locadoraveiculo.controller;

import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.model.Month;
import com.locadoraveiculo.locadoraveiculo.model.Rent;
import com.locadoraveiculo.locadoraveiculo.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@Validated
public class RentController {

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<Rent> save(@Valid @RequestBody Rent rent) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentService.save(rent));
    }

    @GetMapping
    public ResponseEntity<List<Rent>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.findAll());
    }

    @GetMapping("total")
    public ResponseEntity<Long> findTotalByInterval(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date start,
                                                    @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date end) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.findAllByReturnDateInterval(start, end));
    }

    @GetMapping("filter")
    public ResponseEntity<List<Rent>> findByDeliveryDateAndCarModel(@RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date deliveryDate,
                                                                    CarModel carModel) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.findByDeliveryDateAndCarModel(deliveryDate, carModel));
    }

    @GetMapping("filter-hibernate")
    public ResponseEntity<List<Rent>> findByDeliveryDateAndCarModelCriteria(@RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date deliveryDate,
                                                                    CarModel carModel) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.findByDeliveryDateAndCarModelCriteria(deliveryDate, carModel));
    }

    @GetMapping("total-value")
    public ResponseEntity<BigDecimal> sumTotalValue() {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.sumTotalValue());
    }

    @GetMapping("/month/total-value")
    public ResponseEntity<BigDecimal> totalRentValueOfMonth(@RequestParam Month month) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.totalRentValueOfMonth(month));
    }

    @PutMapping("/total-value")
    public ResponseEntity<?> updateTotalValueOfRents(@RequestParam BigDecimal value) {
        rentService.updateTotalValueOfRents(value);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rent> update(@PathVariable Long id, @Valid @RequestBody Rent rent) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.update(id, rent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        rentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delivery-date-before")
    public ResponseEntity deleteRentsWithDeliveryDateBeforeBy(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date date) {
        rentService.deleteRentsWithDeliveryDateBeforeBy(date);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

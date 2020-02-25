package com.locadoraveiculo.locadoraveiculo.filter;

import com.locadoraveiculo.locadoraveiculo.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CarModelFilter {
    private String producerName;
    private List<Category> categories;
}

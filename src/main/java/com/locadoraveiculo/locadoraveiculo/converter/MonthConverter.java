package com.locadoraveiculo.locadoraveiculo.converter;

import com.locadoraveiculo.locadoraveiculo.model.Month;
import org.springframework.core.convert.converter.Converter;

public class MonthConverter implements Converter<String, Month> {

    @Override
    public Month convert(String source) {
        return Month.valueOf(source.toUpperCase());
    }
}

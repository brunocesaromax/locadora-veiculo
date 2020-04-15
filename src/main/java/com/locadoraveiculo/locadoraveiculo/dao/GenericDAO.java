package com.locadoraveiculo.locadoraveiculo.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

    T findById(ID id);
    T save(T entity);
    void delete(ID id);
    List<T> filter(T entity, String... properties) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}

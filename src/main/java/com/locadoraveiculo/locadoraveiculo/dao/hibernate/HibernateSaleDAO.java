package com.locadoraveiculo.locadoraveiculo.dao.hibernate;

import com.locadoraveiculo.locadoraveiculo.dao.SaleDAO;
import com.locadoraveiculo.locadoraveiculo.model.Sale;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class HibernateSaleDAO extends HibernateGenericDAO<Sale, Long> implements SaleDAO, Serializable {
/*Ficaria nessa classe apenas os metodos mais específicos da entidade*/
}

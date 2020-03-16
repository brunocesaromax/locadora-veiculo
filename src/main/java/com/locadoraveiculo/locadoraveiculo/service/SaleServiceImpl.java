package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.SaleDAO;
import com.locadoraveiculo.locadoraveiculo.model.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService{

    private final SaleDAO saleDAO;

    @Transactional
    @Override
    public Sale save(Sale sale) {
        sale.getProducts().forEach(product -> product.setSale(sale));
        return saleDAO.save(sale);
    }

    @Override
    public void remove(Long id) {
        saleDAO.delete(id);
    }
}

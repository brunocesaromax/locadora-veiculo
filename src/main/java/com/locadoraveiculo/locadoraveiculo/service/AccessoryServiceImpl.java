package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.AccessoryDAO;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.model.Accessory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryDAO accessoryDAO;

    @Transactional
    @Override
    public Accessory save(Accessory accessory) {
        return accessoryDAO.save(accessory);
    }

    @Override
    public List<Accessory> findAll() {
        return accessoryDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        accessoryDAO.delete(id);
    }

    @Override
    public Accessory update(Long accessoryId, Accessory accessory) {
        Accessory accessoryOld = accessoryDAO.findById(accessoryId);

        if (accessoryOld == null){
            throw new NotFoundException();
        }else{
            BeanUtils.copyProperties(accessory, accessoryOld, "id");
            return accessoryDAO.save(accessoryOld);
        }
    }
}

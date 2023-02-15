package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.dao.InventoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.pojo.InventoryPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    public void add(InventoryPojo p) throws ApiException {
        dao.insert(p);
    }

    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    public void update(Integer id, InventoryPojo p) throws ApiException {
        InventoryPojo ex = getCheck(id);
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }
    public InventoryPojo getCheck(Integer id) throws ApiException {
        InventoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return p;
    }
    public void reduceQuantity(Integer id, Integer quantity) throws ApiException{
        InventoryPojo p = getCheck(id);

        if(p.getQuantity() < quantity){
            throw new ApiException(quantity+" units not available."+"\nOnly "+ p.getQuantity() + " units left");
        }
        p.setQuantity(p.getQuantity()-quantity);
    }
    public void checkAlreadyExist(Integer id,String barCode) throws ApiException {
        InventoryPojo p = dao.select(id);
        if (p != null) {
            throw new ApiException("Inventory for Barcode: "+barCode+" Already Exists.");
        }
    }
}

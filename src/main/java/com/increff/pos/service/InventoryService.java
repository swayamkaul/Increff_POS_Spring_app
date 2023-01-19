package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.util.StringUtil;
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

    public void delete(int id) {
        dao.delete(id);
    }

    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public InventoryPojo get(String barCode) throws ApiException {
        return getCheck(barCode);
    }

    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    public void update(int id, InventoryPojo p) throws ApiException {
        InventoryPojo ex = getCheck(id);
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }
    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return p;
    }
    public InventoryPojo getCheck(String barCode) throws ApiException {
        InventoryPojo p = dao.select(barCode);
        if (p == null) {
            throw new ApiException("Inventory with given barcode does not exist,Barcode : " + barCode);
        }
        return p;
    }
    public void reduceQuantity(int id, int quantity) throws ApiException{
        InventoryPojo p = getCheck(id);
        if(p.getQuantity() < quantity){
            throw new ApiException(quantity+" units not available."+"\nOnly "+ p.getQuantity() + " units left");
        }
        p.setQuantity(p.getQuantity()-quantity);
    }
    public void increaseQuantity(int id, int quantity) throws ApiException{
        InventoryPojo p = getCheck(id);
        p.setQuantity(p.getQuantity()+quantity);
    }


}

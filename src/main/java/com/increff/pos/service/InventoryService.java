package com.increff.pos.service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.InventoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.pojo.InventoryPojo;

@Service
@Transactional(rollbackFor = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    public void add(InventoryPojo inventoryPojo) throws ApiException {
        InventoryPojo inventoryPojoCheck = get(inventoryPojo.getId());
        if(Objects.isNull(inventoryPojoCheck)) {
            inventoryDao.insert(inventoryPojo);
        }
        else {
            Integer prevQuantity = inventoryPojoCheck.getQuantity();
            Integer newQuantity = prevQuantity + inventoryPojo.getQuantity();
            inventoryPojoCheck.setQuantity(newQuantity);
        }
    }

    public List<InventoryPojo> getAll() {
        return inventoryDao.selectAll();
    }

    public void updateInventoryQuantity(Integer id, Integer quantity) throws ApiException {
        InventoryPojo ex = getCheck(id);
        ex.setQuantity(quantity);
        inventoryDao.update(ex);
    }
    public InventoryPojo getCheck(Integer id) throws ApiException {
        InventoryPojo p = inventoryDao.select(id);
        if (p == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return p;
    }
    public InventoryPojo get(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.select(id);
        return inventoryPojo;
    }
    public void reduceQuantity(Integer id, Integer quantity) throws ApiException{
        InventoryPojo p = getCheck(id);

        if(p.getQuantity() < quantity){
            throw new ApiException(quantity+" units not available."+"\nOnly "+ p.getQuantity() + " units left");
        }
        p.setQuantity(p.getQuantity()-quantity);
    }
    public HashMap<Integer, InventoryPojo> getInventoryMapByIdList(List<Integer> idList) throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryDao.selectByIdList(idList);
        HashMap<Integer,InventoryPojo> idInventoryPojoHashMap=new HashMap<>();
        for(InventoryPojo inventoryPojo: inventoryPojoList){
            idInventoryPojoHashMap.put(inventoryPojo.getId(),inventoryPojo);
        }
        return idInventoryPojoHashMap;
    }
}

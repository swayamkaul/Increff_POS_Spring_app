package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.pojo.ProductPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao dao;

    public void add(ProductPojo p) throws ApiException {
        if(StringUtil.isEmpty(p.getName())) {
            throw new ApiException("name cannot be empty");
        }
        dao.insert(p);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }
    public ProductPojo get(String barCode) throws ApiException {
        return getCheck(barCode);
    }
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }
    public void update(int id, ProductPojo p) throws ApiException {
        ProductPojo ex = getCheck(id);
        ex.setMrp(p.getMrp());
        ex.setBarCode(p.getBarCode());
        ex.setName(p.getName());
        ex.setBrandCategory(p.getBrandCategory());
        dao.update(ex);
    }
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return p;
    }
    public ProductPojo getCheck(String barCode) throws ApiException {
        ProductPojo p = dao.select(barCode);
        if (p == null) {
            throw new ApiException("Product with given barcode does not exist,Barcode : " + barCode);
        }
        return p;
    }

}

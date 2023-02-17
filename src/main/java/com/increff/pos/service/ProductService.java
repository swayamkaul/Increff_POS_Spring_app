package com.increff.pos.service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.pojo.ProductPojo;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public void add(ProductPojo p) throws ApiException {
        productDao.insert(p);
    }

    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }
    public void update(Integer id, ProductPojo p) throws ApiException {
        ProductPojo ex = getCheck(id);
        ex.setMrp(p.getMrp());
        ex.setBarCode(p.getBarCode());
        ex.setName(p.getName());
        ex.setBrandCategory(p.getBrandCategory());
        productDao.update(ex);
    }
    public ProductPojo getCheck(Integer id) throws ApiException {
        ProductPojo p = productDao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return p;
    }
    public ProductPojo getCheck(String barCode) throws ApiException {
        ProductPojo p = productDao.select(barCode);
        if (p == null) {
            throw new ApiException("Product with given barcode does not exist,Barcode : " + barCode);
        }
        return p;
    }
    public void checkExist(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.select(barcode);
        if (!Objects.isNull(productPojo)){
            throw new ApiException("Same Barcode already exists");
        }

    }
    public HashMap<String,ProductPojo> selectInBarcodes(List<String> barcode) throws ApiException {
        List<ProductPojo> productPojoList = productDao.selectInBarcode(barcode);
        HashMap<String,ProductPojo> barCodeProductPojoHashMap=new HashMap<>();
        for(ProductPojo productPojo: productPojoList){
            barCodeProductPojoHashMap.put(productPojo.getBarCode(),productPojo);
        }
        return barCodeProductPojoHashMap;
    }

    public List<ProductPojo> getCheckInBarcodes(List<String> barcode) throws ApiException {
        List<ProductPojo> productPojoList = productDao.selectInBarcode(barcode);
        String error = "Following Barcode not found in Product Database: ";
        for (String s : barcode) {
            if (!productPojoList.stream().anyMatch(productPojo -> productPojo.getBarCode().contentEquals(s))) {
                error += s + ", ";
            }
        }
        if(productPojoList.size() != barcode.size()){
            throw new ApiException(error);
        }
        return productPojoList;
    }


}

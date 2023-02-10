package com.increff.pos.service;

import java.util.List;
import java.util.Objects;

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
    private ProductDao productDao;

    public void add(ProductPojo p) throws ApiException {
        if(StringUtil.isEmpty(p.getName())) {
            throw new ApiException("name cannot be empty");
        }
        productDao.insert(p);
    }

    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }
    public void update(int id, ProductPojo p) throws ApiException {
        ProductPojo ex = getCheck(id);
        ex.setMrp(p.getMrp());
        ex.setBarCode(p.getBarCode());
        ex.setName(p.getName());
        ex.setBrandCategory(p.getBrandCategory());
        productDao.update(ex);
    }
    public ProductPojo getCheck(int id) throws ApiException {
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
    public List<ProductPojo> selectInBarcode(List<String> barcode) throws ApiException {
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

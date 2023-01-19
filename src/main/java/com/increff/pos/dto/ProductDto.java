package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.NormaliseUtil;
import com.increff.pos.util.StringUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDto {
    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService ;

    public void add(ProductForm f) throws ApiException {
        //TODO getCheckBrandPojo
        BrandPojo brandPojo = brandService.get(f.getBrand(),f.getCategory());
        ProductPojo p=ConvertorUtil.convert(f,brandPojo.getId());
        NormaliseUtil.normalise(p);
        ValidationUtil.validate(p);
        try{
            productService.getCheck(p.getId());
        }
        catch(ApiException e){
            productService.add(p);
        }
    }

    public void delete(int id) {
        productService.delete(id);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo productPojo= productService.get(id);
        BrandPojo brandPojo=brandService.get(productPojo.getBrandCategory());
        return ConvertorUtil.convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory());
    }

    public ProductData get(String barCode) throws ApiException {
        ProductPojo productPojo= productService.get(barCode);
        BrandPojo brandPojo=brandService.get(productPojo.getBrandCategory());
        return ConvertorUtil.convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory());
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            BrandPojo brandPojo=brandService.get(p.getBrandCategory());
            list2.add(ConvertorUtil.convert(p,brandPojo.getBrand(),brandPojo.getCategory()));
        }
        return list2;
    }

    public void update(int id, ProductForm f) throws ApiException {
        BrandPojo brandPojo = brandService.get(f.getBrand(),f.getCategory());
        ProductPojo p= ConvertorUtil.convert(f,brandPojo.getId());
        NormaliseUtil.normalise(p);
        ValidationUtil.validate(p);
        productService.update(id,p);
    }

}

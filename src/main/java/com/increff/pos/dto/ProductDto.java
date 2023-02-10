package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDto {
    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService ;

    public void add(List<ProductForm> productForms) throws ApiException{
        JSONArray errorList=new JSONArray();
        Integer errorCount=0;
        for(ProductForm form: productForms) {
            JSONObject error= initialiseBrandErrorObject(form);
            try{
                ValidateUtil.validateForms(form);
                NormaliseUtil.normalise(form);
                brandService.getCheck(form.getBrand(), form.getCategory());
                productService.checkExist(form.getBarCode());
            }
            catch (Exception e) {
                errorCount++;
                error.put("message",e.getMessage());
            }
            errorList.put(error);
        }
        if(errorCount>0){
            throw new ApiException(errorList.toString());
        }
        bulkAdd(productForms);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo productPojo= productService.getCheck(id);
        BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory());
    }

    public ProductData get(String barCode) throws ApiException {
        ProductPojo productPojo= productService.getCheck(barCode);
        BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory());
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            BrandPojo brandPojo=brandService.getCheck(p.getBrandCategory());
            list2.add(ConvertorUtil.convert(p,brandPojo.getBrand(),brandPojo.getCategory()));
        }
        return list2;
    }

    public void update(int id, ProductForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        NormaliseUtil.normalise(f);
        BrandPojo brandPojo = brandService.getCheck(f.getBrand(),f.getCategory());
        ProductPojo p= ConvertorUtil.convert(f,brandPojo.getId());
        productService.update(id,p);
    }
    @Transactional(rollbackOn = ApiException.class)
    private void bulkAdd(List<ProductForm> forms) throws ApiException {
        for(ProductForm form: forms) {
            productService.add(ConvertorUtil.convert(form, brandService.getCheck(form.getBrand(),
                    form.getCategory()).getId()));
        }
    }

    JSONObject initialiseBrandErrorObject(ProductForm productForm){
        JSONObject error=new JSONObject();
        error.put("barCode",productForm.getBarCode());
        error.put("brand",productForm.getBrand());
        error.put("category",productForm.getCategory());
        error.put("name",productForm.getName());
        error.put("mrp",productForm.getMrp());
        error.put("message","");
        return error;
    }

}

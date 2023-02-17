package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductErrorData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDto {
    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService ;

    public void add(List<ProductForm> productFormList) throws ApiException, JsonProcessingException {
        listEmptyCheck(productFormList);
        List<ProductErrorData> errorData = new ArrayList<>();
        Integer errorSize = 0;
        for(ProductForm form: productFormList) {
            ProductErrorData productErrorData= ConvertorUtil.convertToErrorData(form);
            try{
                ValidateUtil.validateForms(form);
                NormaliseUtil.normalise(form);
                brandService.getCheck(form.getBrand(), form.getCategory());
                productService.checkExist(form.getBarCode());
            }
            catch (Exception e) {
                errorSize++;
                productErrorData.setMessage(e.getMessage());
            }
            errorData.add(productErrorData);
        }

        if(errorSize > 0){
            ErrorUtil.throwErrors(errorData);
        }
        bulkAdd(productFormList);
    }


    public ProductData get(Integer id) throws ApiException {
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

    public void update(Integer id, ProductForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        NormaliseUtil.normalise(f);
        ProductData productDataPrev=get(id);
        if((!productDataPrev.getBarCode().equals(f.getBarCode())) ||
           (!productDataPrev.getBrand().equals(f.getBrand()))||
           (!productDataPrev.getCategory().equals(f.getCategory()))){
            throw new ApiException("Barcode, Brand, Category cannot be changed!");
        }
        BrandPojo brandPojo = brandService.getCheck(f.getBrand(),f.getCategory());
        ProductPojo productPojoNew= ConvertorUtil.convert(f,brandPojo.getId());
        productService.update(id,productPojoNew);
    }

    @Transactional(rollbackFor = ApiException.class)
    private void bulkAdd(List<ProductForm> forms) throws ApiException {
        for(ProductForm form: forms) {
            productService.add(ConvertorUtil.convert(form, brandService.getCheck(form.getBrand(),
                    form.getCategory()).getId()));
        }
    }
    private void listEmptyCheck(List<ProductForm> productFormList) throws ApiException {
        if(productFormList.isEmpty())
            throw new ApiException("Product List is Empty!");
    }

}

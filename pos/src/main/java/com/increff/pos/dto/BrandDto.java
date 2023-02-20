package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandErrorData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BrandDto {
    @Autowired
    BrandService brandService;
    @Autowired
    CsvFileGenerator csvFileGenerator;
    public void add(List<BrandForm> brandForms) throws ApiException, JsonProcessingException {
        listEmptyCheck(brandForms);
        List<BrandErrorData> errorData = new ArrayList<>();
        Integer errorSize = 0;
        for (BrandForm brandForm : brandForms) {
            BrandErrorData brandErrorData = ConvertorUtil.convertToErrorData(brandForm);
            try {
                ValidateUtil.validateForms(brandForm);
                NormaliseUtil.normalise(brandForm);
                brandService.checkAlreadyExist(brandForm.getBrand(), brandForm.getCategory());
            } catch (Exception e) {
                errorSize++;
                brandErrorData.setMessage(e.getMessage());
            }
            errorData.add(brandErrorData);
        }
        if (errorSize > 0) {
            ErrorUtil.throwErrors(errorData);
        }
        bulkAdd(brandForms);
    }

    public BrandData get(Integer id) throws ApiException {
       BrandPojo p = brandService.getCheck(id);
       return ConvertorUtil.convert(p);
    }
    public BrandData get(String brand,String category) throws ApiException {
        BrandPojo p = brandService.getCheck(brand,category);
        return ConvertorUtil.convert(p);
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo p : list) {
            list2.add(ConvertorUtil.convert(p));
        }
        return list2;
    }

    public void update(Integer id, BrandForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        NormaliseUtil.normalise(f);
        BrandPojo p = ConvertorUtil.convert(f);
        brandService.update(id,p);
    }

    public void generateCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"BrandReport.csv\"");
        csvFileGenerator.writeBrandsToCsv(brandService.getAll(), response.getWriter());
    }

    @Transactional(rollbackFor = ApiException.class)
    private void bulkAdd(List<BrandForm> brandForms) throws ApiException {
        for (BrandForm brandForm: brandForms){
            BrandPojo b = ConvertorUtil.convert(brandForm);
            brandService.add(b);
        }
    }

    private void listEmptyCheck(List<BrandForm> brandFormList) throws ApiException {
        if(brandFormList.isEmpty())
            throw new ApiException("Brand List is Empty!");
    }
}

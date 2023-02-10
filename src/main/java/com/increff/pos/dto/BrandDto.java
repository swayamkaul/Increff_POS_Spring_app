package com.increff.pos.dto;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@Component
public class BrandDto {
    @Autowired
    BrandService brandService;
    @Autowired
    CsvFileGenerator csvFileGenerator;
    public void add(List<BrandForm> brandFormList) throws ApiException{
        JSONArray errorList=new JSONArray();
        Integer errorCount=0;
        listEmptyCheck(brandFormList);
        for (BrandForm brandForm : brandFormList) {
            JSONObject error= initialiseBrandErrorObject(brandForm);
            try {
                ValidateUtil.validateForms(brandForm);
                NormaliseUtil.normalise(brandForm);
                brandService.checkAlreadyExist(brandForm.getBrand(), brandForm.getCategory());

            } catch (Exception e) {
                error.put("message",e.getMessage());
                errorCount++;
            }
            errorList.put(error);
        }
        if(errorCount>0){
            throw new ApiException(errorList.toString());
        }
        bulkAdd(brandFormList);
    }

    public BrandData get(int id) throws ApiException {
       BrandPojo p= brandService.getCheck(id);
       return ConvertorUtil.convert(p);
    }
    public BrandData get(String brand,String category) throws ApiException {
        BrandPojo p= brandService.getCheck(brand,category);
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

    public void update(int id, BrandForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        NormaliseUtil.normalise(f);
        BrandPojo p= ConvertorUtil.convert(f);

        brandService.update(id,p);
    }

    public void generateCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"BrandReport.csv\"");
        csvFileGenerator.writeBrandsToCsv(brandService.getAll(), response.getWriter());
    }

    @Transactional(rollbackOn = ApiException.class)
    private void bulkAdd(List<BrandForm> brandForms) throws ApiException {
        for (BrandForm brandForm: brandForms){
            BrandPojo b = ConvertorUtil.convert(brandForm);
            brandService.add(b);
        }
    }
    JSONObject initialiseBrandErrorObject(BrandForm brandForm){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("brand",brandForm.getBrand());
        jsonObject.put("category",brandForm.getCategory());
        jsonObject.put("message","");
        return jsonObject;
    }

    private void listEmptyCheck(List<BrandForm> brandFormList) throws ApiException {
        if(brandFormList.isEmpty())
            throw new ApiException("Brand List is Empty!");
    }
}

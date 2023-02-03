package com.increff.pos.dto;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.CsvFileGenerator;
import com.increff.pos.util.NormaliseUtil;
import com.increff.pos.util.ValidateUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
//TODO create validationUtil class, function checkValid, you'll pass your object, and it should through exception according to validation annotations DONE
public class BrandDto {
    @Autowired
    BrandService service;
    @Autowired
    CsvFileGenerator csvFileGenerator;
    public void add(BrandForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        BrandPojo p= ConvertorUtil.convert(f);
        NormaliseUtil.normalise(p);
        try{
            service.getCheck(p.getId());
        }
        catch(ApiException e){
            service.add(p);
        }
    }

    public void delete(int id) {
        service.delete(id);
    }

    public BrandData get(int id) throws ApiException {
       BrandPojo p=service.getCheck(id);
       return ConvertorUtil.convert(p);
    }
    public BrandData get(String brand,String category) throws ApiException {
        BrandPojo p=service.getCheck(brand,category);
        return ConvertorUtil.convert(p);
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = service.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo p : list) {
            list2.add(ConvertorUtil.convert(p));
        }
        return list2;
    }

    public void update(int id, BrandForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        BrandPojo p= ConvertorUtil.convert(f);
        NormaliseUtil.normalise(p);
        service.update(id,p);
    }

    public void generateCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"BrandReport.csv\"");
        csvFileGenerator.writeBrandsToCsv(service.getAll(), response.getWriter());
    }
}

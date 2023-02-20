package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.FormHelper;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;

    @Autowired
    private BrandService brandService;


    @Test(expected = ApiException.class)
    public void testEmptyListBrandAddition() throws ApiException, JsonProcessingException {
        List<BrandForm> brandFormList = new ArrayList<>();
        brandDto.addBrandList(brandFormList);
    }

    @Test
    public void testBrandAddition() throws ApiException, JsonProcessingException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("BrandCheck", "CategoryCheck");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        String expectedBrandName = "brandcheck";
        String expectedCategory = "categorycheck";

        BrandPojo brandPojo = brandService.getCheck(expectedBrandName, expectedCategory);
        assertEquals(expectedBrandName, brandPojo.getBrand());
        assertEquals(expectedCategory, brandPojo.getCategory());
    }

    @Test
    public void testGetAllBrands() throws ApiException, JsonProcessingException {
        List<BrandForm> brandForms = new ArrayList<>();
        BrandForm brand1 = FormHelper.createBrand("Brand1", "Category1");
        brandForms.add(brand1);
        BrandForm brand2 = FormHelper.createBrand("Brand2", "Category2");
        brandForms.add(brand2);
        brandDto.addBrandList(brandForms);
        List<BrandData> allBrands = brandDto.getAllBrands();
        assertEquals(2, allBrands.size());
    }

    @Test
    public void testUpdateBrand() throws ApiException, JsonProcessingException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand1", "CateGory1");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        String expectedBrand = "brand1";
        String expectedCategory = "category1";

        BrandPojo brandPojo =brandService.getCheck(expectedBrand,expectedCategory);

        String newBrand = "brand2";
        String newCategory = "catogory2";
        brandForm.setBrand(newBrand);
        brandForm.setCategory(newCategory);
        brandDto.updateBrand(brandPojo.getId(),brandForm);

        BrandPojo pojo = brandService.getCheck(brandPojo.getId());
        assertEquals(newBrand, pojo.getBrand());
        assertEquals(newCategory, pojo.getCategory());
    }

    @Test
    public void testGetCheckbyId() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        String expectedBrand = "brand";
        String expectedCategory = "category";

        BrandPojo brandPojo =brandService.getCheck(expectedBrand,expectedCategory);

        BrandData brandDataById = brandDto.getBrand(brandPojo.getId());
        assertEquals(brandDataById.getBrand(), brandPojo.getBrand());
        assertEquals(brandDataById.getCategory(), brandPojo.getCategory());

    }
    @Test
    public void testGetCheckbyBrandAndCategory() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        String expectedBrand = "brand";
        String expectedCategory = "category";

        BrandPojo brandPojo =brandService.getCheck(expectedBrand,expectedCategory);
        BrandData brandDataByBrandAndCategory = brandDto.getBrand(brandPojo.getBrand(),brandPojo.getCategory());

        assertEquals(brandDataByBrandAndCategory.getBrand(), brandPojo.getBrand());
        assertEquals(brandDataByBrandAndCategory.getCategory(), brandPojo.getCategory());
    }

    @Test
    public void testCsvDownload() throws IOException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);
        MockHttpServletResponse response = new MockHttpServletResponse();
        brandDto.getCsvReport(response);
        assertEquals("text/csv", response.getContentType());
    }

    @Test(expected = ApiException.class)
    public void testAddDuplicate() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);
        brandDto.addBrandList(brandFormList);
    }

    @Test(expected = ApiException.class)
    public void testBlankBrand() throws JsonProcessingException, ApiException {
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);
    }

}

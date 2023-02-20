package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    @Test(expected = ApiException.class)
    public void testProductAddition() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarCode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        Integer expectedBrandCategory = 2;
        String expectedName = "name";
        Double expectedMrp = 23.00;
        String expectedBarCode = "12345678";

        ProductPojo data = productService.getCheck(productService.getCheck(expectedBarCode).getId());
        assertEquals(expectedBarCode, data.getBarCode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedMrp, data.getMrp(), 0.001);
        assertEquals(expectedBrandCategory, data.getBrandCategory());
        productService.checkExist("12345678");
    }

    @Test
    public void testGetAllProduct() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarCode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo productPojo1 = new ProductPojo();
        productPojo1.setBarCode("12345679");
        productPojo1.setName("name");
        productPojo1.setBrandCategory(3);
        productPojo1.setMrp(23.00);
        productService.add(productPojo1);

        List<ProductPojo> list = productService.getAll();

        assertEquals(2, list.size());
    }

    @Test
    public void testUpdate() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarCode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo productPojo1 = new ProductPojo();
        productPojo1.setBarCode("12345678");
        productPojo1.setName("name2");
        productPojo1.setBrandCategory(2);
        productPojo1.setMrp(27.00);

        Integer expectedBrandCategory = 2;
        String expectedName = "name2";
        Double expectedMrp = 27.00;
        String expectedBarCode = "12345678";

        ProductPojo pojo = productService.getCheck(productService.getCheck(expectedBarCode).getId());
        productService.update(pojo.getId(), productPojo1);
        ProductPojo data = productService.getCheck(pojo.getId());
        assertEquals(expectedBarCode, data.getBarCode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedMrp, data.getMrp(), 0.001);
        assertEquals(expectedBrandCategory, data.getBrandCategory());

    }

    @Test(expected = ApiException.class)
    public void testCheckId() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarCode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(brandService.getCheck(brandPojo.getBrand(),brandPojo.getCategory()).getId());
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        Integer id = productService.getCheck("23456789").getId();
    }

    @Test(expected = ApiException.class)
    public void testCheckIllegal() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);

        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarCode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(brandService.getCheck(brandPojo.getBrand(),brandPojo.getCategory()).getId());
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo pojo = productService.getCheck(9);
    }


    @Test
    public void testSelectInBarCode() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarCode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo productPojo1 = new ProductPojo();
        productPojo1.setBarCode("12345679");
        productPojo1.setName("name");
        productPojo1.setBrandCategory(3);
        productPojo1.setMrp(23.00);
        productService.add(productPojo1);

        List<String> barCodes = new ArrayList<>();
        barCodes.add("12345678");
        barCodes.add("12345679");
        List<ProductPojo> list = productService.getCheckBarcodeList(barCodes);
        assertEquals(2, list.size());

    }

    @Test(expected = ApiException.class)
    public void testSelectInBarCodeIllegal() throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarCode("12345678");
        productPojo.setName("name");
        productPojo.setBrandCategory(2);
        productPojo.setMrp(23.00);
        productService.add(productPojo);

        ProductPojo productPojo1 = new ProductPojo();
        productPojo1.setBarCode("12345679");
        productPojo1.setName("name");
        productPojo1.setBrandCategory(3);
        productPojo1.setMrp(23.00);
        productService.add(productPojo1);

        List<String> barCodes = new ArrayList<>();
        barCodes.add("12345678");
        barCodes.add("12345679");
        barCodes.add("12345680");
        List<ProductPojo> list = productService.getCheckBarcodeList(barCodes);
        assertEquals(3, list.size());

    }

}

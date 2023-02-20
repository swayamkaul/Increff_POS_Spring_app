package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.InventoryPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryServiceTest extends AbstractUnitTest {
    @Autowired
    InventoryService inventoryService;

    @Test
    public void testInventoryAddition() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(3);
        inventoryService.add(inventoryPojo);

        Integer expectedID = 1;
        Integer expectedQuantity = 3;

        InventoryPojo pojo = inventoryService.getCheck(expectedID);
        assertEquals(expectedID, pojo.getId());
        assertEquals(expectedQuantity, pojo.getQuantity());

        inventoryService.add(inventoryPojo);

        expectedQuantity = 6;

        pojo = inventoryService.getCheck(expectedID);
        assertEquals(expectedID, pojo.getId());
        assertEquals(expectedQuantity, pojo.getQuantity());

    }

    @Test
    public void testGetAll() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(3);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo2 = new InventoryPojo();
        inventoryPojo2.setId(2);
        inventoryPojo2.setQuantity(3);
        inventoryService.add(inventoryPojo2);

        List<InventoryPojo> list = inventoryService.getAll();
        assertEquals(2, list.size());
    }

    @Test
    public void testUpdate() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(3);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo2 = new InventoryPojo();
        inventoryPojo2.setId(1);
        inventoryPojo2.setQuantity(9);

        inventoryService.updateInventoryQuantity(1, inventoryPojo2.getQuantity());
        Integer expectedQuantity = 9;

        InventoryPojo pojo = inventoryService.getCheck(1);
        assertEquals(expectedQuantity, pojo.getQuantity());

    }

    @Test(expected = ApiException.class)
    public void testGetCheckId() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(3);
        inventoryService.add(inventoryPojo);
        Integer expextedQuantity = 3;
        InventoryPojo pojo = inventoryService.getCheck(1);
        assertEquals(expextedQuantity, pojo.getQuantity());
        InventoryPojo pojo2 = inventoryService.getCheck(3);


    }

    @Test(expected = ApiException.class)
    public void testReduceInventory() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(3);
        inventoryService.add(inventoryPojo);

        inventoryService.reduceQuantity(1, 4);
    }


}

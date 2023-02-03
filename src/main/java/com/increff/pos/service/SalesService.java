package com.increff.pos.service;

import com.increff.pos.dao.SalesDao;
import com.increff.pos.pojo.SalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class SalesService {
    @Autowired
    SalesDao salesDao;

    public void add(SalesPojo salesPojo){
        salesDao.insert(salesPojo);
    }

    public List<SalesPojo> getALL() {
        return salesDao.selectAllDesc();
    }

    public SalesPojo getByDate(LocalDate date) {
        return salesDao.selectByDate(date);
    }

    public void update(LocalDate date, SalesPojo newPojo)
    {
        SalesPojo pojo = salesDao.selectByDate(date);
        pojo.setInvoicedOrderCount(newPojo.getInvoicedOrderCount());
        pojo.setTotalRevenue(newPojo.getTotalRevenue());
        pojo.setInvoicedItemsCount(newPojo.getInvoicedItemsCount());
        pojo.setLastRun(newPojo.getLastRun());
    }

    public List<SalesPojo> getAllByDate(LocalDate startDate, LocalDate endDate) {
        return salesDao.getAllByDate(startDate,endDate);
    }

}

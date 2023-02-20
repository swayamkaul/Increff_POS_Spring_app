package com.increff.pos.scheduler;

import com.increff.pos.dto.DailySalesDto;
import com.increff.pos.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
public class SalesScheduler {
    @Autowired
    DailySalesDto dailySalesDto;

    @Async
    @Scheduled(cron = "0 0 12 * * *")
    public void createReport() throws ApiException {
        dailySalesDto.generateReport();
    }
}

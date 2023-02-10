package com.increff.pos.util;

import com.increff.pos.service.ApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateValidatorUtil {
    public static void isValidDateTimeRange(LocalDateTime start, LocalDateTime end) throws ApiException {
        if (start.isAfter(end)) {
            throw new ApiException("Start date cannot be after end date");
        }
        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();
        long days = endDate.toEpochDay() - startDate.toEpochDay();
        if(days > 30) {
            throw new ApiException("Date range cannot be more than 30 days");
        }
    }
    public static void isValidDateRange(LocalDate start, LocalDate end) throws ApiException {
        if (start.isAfter(end)) {
            throw new ApiException("Start date cannot be after end date");
        }
        long days = end.toEpochDay() - start.toEpochDay();
        if(days > 30) {
            throw new ApiException("Date range cannot be more than 30 days");
        }
    }
}

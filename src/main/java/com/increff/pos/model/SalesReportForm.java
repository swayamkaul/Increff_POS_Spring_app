package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class SalesReportForm {

    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;
    @NotNull    // Application Level constraint required not DB level Constraint
    private String brand;
    @NotNull
    private String category;
}

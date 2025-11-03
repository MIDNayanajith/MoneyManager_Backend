package com.example.moneyManager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilterDto {

    private String type;
    private LocalDate startDate;
    private  LocalDate endDate;
    private String keyword;
    private String sortField; //data,amount,name
    private String sortOrder; // asc or desc
}

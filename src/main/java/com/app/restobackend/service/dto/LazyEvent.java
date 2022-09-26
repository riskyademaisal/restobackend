package com.app.restobackend.service.dto;

import lombok.Data;

@Data
public class LazyEvent {
    
    private int first;
    private int rows;
    private String sortField;
    private String sortOrder;
    private String multiSortMeta;
    private String filters;

}

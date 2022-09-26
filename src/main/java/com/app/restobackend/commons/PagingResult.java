package com.app.restobackend.commons;

import lombok.Data;

import java.util.List;

@Data
public class PagingResult<T> {
    private List<T> content;
    private int total;
    private int page;
    private int length;
}

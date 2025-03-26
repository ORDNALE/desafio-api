package com.emagalha.desafio_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class PageDTO<T> {
    private List<T> content;
    private Integer totalElements;
    private int totalPages; 
    private int currentPage;  
    private int pageSize; 

    public PageDTO(List<T> content, Integer totalElements, int totalPages, int currentPage, int pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}

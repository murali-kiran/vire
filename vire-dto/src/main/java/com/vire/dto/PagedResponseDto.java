package com.vire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResponseDto<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private List<T> pageItems;
    private Long totalItems;
    private Integer totalPages;
}

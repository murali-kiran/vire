package com.vire.model.response;

import com.vire.dto.MasterDto;
import com.vire.dto.PagedResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Data
@AllArgsConstructor
public class PagedResponse<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private List<T> pageItems;
    private Long totalItems;
    private Integer totalPages;

}

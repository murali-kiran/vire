package com.vire.model.response;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageWiseSearchResponse<T> {
    Integer pageCount;
    List<T> list;
}

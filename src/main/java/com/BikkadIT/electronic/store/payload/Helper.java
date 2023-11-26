package com.BikkadIT.electronic.store.payload;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


public class Helper {
    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {

        List<U> entity = page.getContent();

        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());
        response.setPageNumber(page.getNumber());

        return response;
}}

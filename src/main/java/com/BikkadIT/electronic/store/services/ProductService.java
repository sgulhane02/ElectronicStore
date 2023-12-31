package com.BikkadIT.electronic.store.services;

import com.BikkadIT.electronic.store.dtos.ProductDto;
import com.BikkadIT.electronic.store.payload.PageableResponse;

import java.util.List;

public interface ProductService{
    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto ,String productId);

    void deleteProduct(String productId);

    ProductDto getProductById(String productId);

    PageableResponse<ProductDto> getAllProducts(Integer pageNum, Integer pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllLive(Integer pageNum, Integer pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchByTitle(String subTitle,Integer pageNum, Integer pageSize, String sortBy, String sortDir);
}

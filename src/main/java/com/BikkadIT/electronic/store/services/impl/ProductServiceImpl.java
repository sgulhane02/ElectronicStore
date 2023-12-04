package com.BikkadIT.electronic.store.services.impl;

import com.BikkadIT.electronic.store.dtos.ProductDto;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.repositories.ProductRepository;
import com.BikkadIT.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {


        return null;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        return null;
    }

    @Override
    public void deleteProduct(String productId) {

    }

    @Override
    public ProductDto getProductById(String productId) {
        return null;
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNum, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<ProductDto> getAllLive() {
        return null;
    }

    @Override
    public List<ProductDto> searchByTitle(String subTitle) {
        return null;
    }
}

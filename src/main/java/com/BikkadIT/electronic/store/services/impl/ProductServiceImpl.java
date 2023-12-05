package com.BikkadIT.electronic.store.services.impl;

import com.BikkadIT.electronic.store.constants.AppConstant;
import com.BikkadIT.electronic.store.dtos.ProductDto;
import com.BikkadIT.electronic.store.entities.Product;
import com.BikkadIT.electronic.store.exceptions.ResourceNotFound;
import com.BikkadIT.electronic.store.helper.PageableHelper;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.repositories.ProductRepository;
import com.BikkadIT.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${product.profile.image.path}")
    private String path;

    /**
     *
     * @param productDto
     * @return
     */
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        Product save = this.productRepository.save(product);
        return this.modelMapper.map(product,ProductDto.class);

    }

    /**
     *
     * @param productDto
     * @param productId
     * @return
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + "id" + productId));
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setStock(productDto.getStock());
        product.setLive(productDto.getLive());
        product.setAddedDate(new Date());
        product.setQuantity(productDto.getQuantity());
        Product updatedProduct = this.productRepository.save(product);
        return this.modelMapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + "id" + productId));
        String imageName = product.getImageName();
        String fullPath=path+imageName;
        File file=new File(fullPath);
        if(file.exists()){
            file.delete();
        }
        this.productRepository.delete(product);

    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFound(AppConstant.NOT_FOUND + "id" + productId));
        return this.modelMapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNum, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("dsc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = PageableHelper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(Integer pageNum, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("dsc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response = PageableHelper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public List<ProductDto> searchByTitle(String subTitle) {
        return null;
    }
}

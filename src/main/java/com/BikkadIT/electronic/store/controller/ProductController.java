package com.BikkadIT.electronic.store.controller;

import com.BikkadIT.electronic.store.constants.AppConstant;
import com.BikkadIT.electronic.store.constants.UrlConstant;
import com.BikkadIT.electronic.store.dtos.ProductDto;
import com.BikkadIT.electronic.store.payload.ApiResponseMessage;
import com.BikkadIT.electronic.store.payload.ImageResponse;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.repositories.ProductRepository;
import com.BikkadIT.electronic.store.services.ImageService;
import com.BikkadIT.electronic.store.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Slf4j
@RequestMapping(UrlConstant.BASE_URL)
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    @Value("${product.profile.image.path}")
    private String path;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        log.info("Entering request for create product");
        ProductDto product = this.productService.createProduct(productDto);
        log.info("Completed request for create product");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId){
        log.info("Entering request for get product with product id:{}"+productId);
        ProductDto productById = this.productService.getProductById(productId);
        log.info("Completed request for get product with product id:{}"+productId);
        return new ResponseEntity<>(productById,HttpStatus.OK);
    }
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        log.info("Entering request for delete product with product id:{}"+productId);
        this.productService.deleteProduct(productId);
        ApiResponseMessage response = ApiResponseMessage.builder().message(AppConstant.DELETED).status(true).httpStatus(HttpStatus.OK).build();
        log.info("Completed request for delete product with product id:{}"+productId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNum",defaultValue = UrlConstant.PAGE_NUMBER,required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = UrlConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = UrlConstant.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = UrlConstant.SORT_DIRECTION,required = false) String sortDir
    ){
        log.info("Entering request for get all products with pagination");
        PageableResponse<ProductDto> allProducts = this.productService.getAllProducts(pageNum, pageSize, sortBy, sortDir);
        log.info("Completed request for get all products with pagination");
        return new ResponseEntity<PageableResponse<ProductDto>>(allProducts,HttpStatus.OK);
    }
    @GetMapping("/product/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNum",defaultValue = UrlConstant.PAGE_NUMBER,required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = UrlConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = UrlConstant.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = UrlConstant.SORT_DIRECTION,required = false) String sortDir
    ){
        log.info("Entering request for get all live products with pagination");
        PageableResponse<ProductDto> allProducts = this.productService.getAllLive(pageNum, pageSize, sortBy, sortDir);
        log.info("Completed request for get all live products with pagination");
        return new ResponseEntity<PageableResponse<ProductDto>>(allProducts,HttpStatus.OK);
    }
    @GetMapping("product/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchByTitle(
            @PathVariable String subTitle,
            @RequestParam(value = "pageNum", defaultValue = UrlConstant.PAGE_NUMBER, required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = UrlConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = UrlConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = UrlConstant.SORT_DIRECTION, required = false) String sortDir
    ) {
        log.info("Entering request for search the products by title with pagination");
        PageableResponse<ProductDto> allProducts = this.productService.searchByTitle(subTitle,pageNum, pageSize, sortBy, sortDir);
        log.info("Entering request for search the products by title with pagination");
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }

    @PostMapping("/product/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable MultipartFile image, String productId) throws IOException {
        log.info("Entering request for upload the product image with product id:{}",productId);
        String file = imageService.uploadFile(image, path);
        ProductDto product = productService.getProductById(productId);
        product.setImageName(file);
        productService.updateProduct(product,productId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(file).message("product image uploaded").success(true).status(HttpStatus.OK).build();
        log.info("Completed request for upload the product image with product id:{}",productId);
        return new ResponseEntity<>(imageResponse,HttpStatus.OK);
    }

    @GetMapping("/product/image/{productId}")
    public void getImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info("Entering request for get the product image with product id:{}",productId);
        ProductDto product = productService.getProductById(productId);
        InputStream resource = imageService.getResource(path, product.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        log.info("Completed request for get the product image with product id:{}",productId);
    }
}

package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.ProductImagesDto;
import com.baykov.springeshop.exceptions.ProductException;
import com.baykov.springeshop.mappers.ProductMapper;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.services.ProductService;
import com.baykov.springeshop.utils.ExceptionUtil;
import com.baykov.springeshop.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductValidator productValidator;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "products_per_page", required = false) Integer productsPerPage,
                                         @RequestParam(value = "sorted_by_title_asc", required = false) boolean sortedByTitleAsc,
                                         @RequestParam(value = "sorted_by_price_asc", required = false) boolean sortedByPriceAsc,
                                         @RequestParam(value = "sorted_by_title_desc", required = false) boolean sortedByTitleDesc,
                                         @RequestParam(value = "sorted_by_price_desc", required = false) boolean sortedByPriceDesc) {
        List<Product> products = productService.getProducts(page, productsPerPage, sortedByTitleAsc, sortedByPriceAsc,
                sortedByTitleDesc, sortedByPriceDesc);
        return new ResponseEntity<>(productMapper.fromProducts(products), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(productMapper.fromProduct(product), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findProducts(@RequestParam(value = "query", required = false) String query) {
        List<Product> products = productService.findProducts(query);
        return new ResponseEntity<>(productMapper.fromProducts(products), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductImagesDto productDto, BindingResult errors) throws Exception {
        productValidator.validate(productDto, errors);
        ExceptionUtil.checkExceptions(errors, ProductException.class);
        Product product = productMapper.toProductFull(productDto);
        productService.saveProduct(product);
        return new ResponseEntity<>(productMapper.fromProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProduct(@PathVariable Long id, @RequestBody @Valid ProductImagesDto productDto,
                                         BindingResult errors) throws Exception {
        productValidator.validate(productDto, errors);
        ExceptionUtil.checkExceptions(errors, ProductException.class);
        Product product = productMapper.toProductFull(productDto);
        product = productService.editProduct(id, product);
        return new ResponseEntity<>(productMapper.fromProduct(product), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return HttpStatus.OK;
    }
}

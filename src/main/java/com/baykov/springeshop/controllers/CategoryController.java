package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.CategoryProductsDto;
import com.baykov.springeshop.exceptions.CategoryException;
import com.baykov.springeshop.exceptions.ExceptionUtil;
import com.baykov.springeshop.mappers.CategoryMapper;
import com.baykov.springeshop.models.Category;
import com.baykov.springeshop.services.CategoryService;
import com.baykov.springeshop.validators.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryValidator categoryValidator;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "categories_per_page", required = false) Integer categoriesPerPage,
                                           @RequestParam(value = "sorted_by_title_asc", required = false) boolean sortedByTitleAsc,
                                           @RequestParam(value = "sorted_by_title_desc", required = false) boolean sortedByTitleDesc) {
        List<Category> categories = categoryService.getCategories(page, categoriesPerPage, sortedByTitleAsc, sortedByTitleDesc);
        return new ResponseEntity<>(categoryMapper.fromCategories(categories), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryMapper.fromCategoryFull(category), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findCategories(@RequestParam(value = "query", required = false) String query) {
        Set<Category> categories = categoryService.findCategories(query);
        return new ResponseEntity<>(categoryMapper.fromCategories(categories), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryProductsDto categoryDto, BindingResult errors) throws Exception {
        categoryValidator.validate(categoryDto, errors);
        ExceptionUtil.checkExceptions(errors, CategoryException.class);
        Category category = categoryMapper.toCategory(categoryDto);
        categoryService.saveCategory(category);
        return new ResponseEntity<>(categoryMapper.fromCategory(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return HttpStatus.OK;
    }

    @PatchMapping("{categoryId}/add/{productId}")
    public HttpStatus addProductToCategory(@PathVariable(value = "categoryId") Long categoryId,
                                           @PathVariable(value = "productId") Long productId) {
        categoryService.addProduct(categoryId, productId);
        return HttpStatus.ACCEPTED;
    }

    @PatchMapping("{categoryId}/remove/{productId}")
    public HttpStatus deleteProductFromCategory(@PathVariable(value = "categoryId") Long categoryId,
                                                @PathVariable(value = "productId") Long productId) {
        categoryService.deleteProduct(categoryId, productId);
        return HttpStatus.OK;
    }
}

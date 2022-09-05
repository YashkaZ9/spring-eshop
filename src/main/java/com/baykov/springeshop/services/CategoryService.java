package com.baykov.springeshop.services;

import com.baykov.springeshop.exceptions.CategoryException;
import com.baykov.springeshop.models.Category;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.repos.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final ProductService productService;

    public List<Category> getCategories(Integer page, Integer categoriesPerPage,
                                        boolean sortedByTitleAsc, boolean sortedByTitleDesc) {
        return page != null && categoriesPerPage != null ?
                getSortedPagedCategories(page, categoriesPerPage, sortedByTitleAsc, sortedByTitleDesc)
                : getSortedPagedCategories(0, Integer.MAX_VALUE, sortedByTitleAsc, sortedByTitleDesc);
    }

    public List<Category> getSortedPagedCategories(Integer page, Integer categoriesPerPage,
                                                   boolean sortedByTitleAsc, boolean sortedByTitleDesc) {
        if (sortedByTitleAsc && sortedByTitleDesc) {
            return categoryRepo.findAll(PageRequest.of(page, categoriesPerPage)).getContent();
        }
        return sortedByTitleAsc ? categoryRepo.findAll(PageRequest.of(page, categoriesPerPage, Sort.by("title"))).getContent()
                : sortedByTitleDesc ? categoryRepo.findAll(PageRequest.of(page, categoriesPerPage, Sort.by(Sort.Order.desc("title")))).getContent()
                : categoryRepo.findAll(PageRequest.of(page, categoriesPerPage)).getContent();
    }

    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new CategoryException("This category does not exist."));
    }

    public Set<Category> findCategories(String query) {
        return categoryRepo.findByTitleContainingIgnoreCase(query);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void saveCategory(Category category) {
        categoryRepo.save(category);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void deleteCategoryById(Long id) {
        categoryRepo.deleteById(id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void addProduct(Long categoryId, Long productId) {
        Category category = getCategoryById(categoryId);
        Product product = productService.getProductById(productId);
        if (category.getProducts() == null) {
            category.setProducts(new HashSet<>());
        }
        category.getProducts().add(product);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void deleteProduct(Long categoryId, Long productId) {
        Category category = getCategoryById(categoryId);
        Product product = productService.getProductById(productId);
        if (category.getProducts() != null) {
            category.getProducts().remove(product);
        }
    }
}

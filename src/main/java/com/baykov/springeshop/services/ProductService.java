package com.baykov.springeshop.services;

import com.baykov.springeshop.exceptions.ProductException;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepo productRepo;
    private final CartService cartService;

    @Autowired
    public ProductService(ProductRepo productRepo, @Lazy CartService cartService) {
        this.productRepo = productRepo;
        this.cartService = cartService;
    }

    public List<Product> getProducts(Integer page, Integer productsPerPage, boolean sortedByTitleAsc, boolean sortedByPriceAsc,
                                     boolean sortedByTitleDesc, boolean sortedByPriceDesc) {
        return page != null && productsPerPage != null ?
                getSortedPagedProducts(page, productsPerPage, sortedByTitleAsc, sortedByPriceAsc, sortedByTitleDesc, sortedByPriceDesc) :
                getSortedPagedProducts(0, Integer.MAX_VALUE, sortedByTitleAsc, sortedByPriceAsc, sortedByTitleDesc, sortedByPriceDesc);
    }

    public List<Product> getSortedPagedProducts(Integer page, Integer productsPerPage, boolean sortedByTitleAsc, boolean sortedByPriceAsc,
                                                boolean sortedByTitleDesc, boolean sortedByPriceDesc) {
        if (sortedByTitleAsc && sortedByTitleDesc && sortedByPriceAsc && sortedByPriceDesc) {
            return productRepo.findAll(PageRequest.of(page, productsPerPage)).getContent();
        }
        if (sortedByTitleAsc && sortedByTitleDesc) {
            return sortedByPriceAsc ? productRepo.findAll(PageRequest.of(page, productsPerPage, Sort.by("price"))).getContent()
                    : sortedByPriceDesc ? productRepo.findAll(PageRequest.of(page, productsPerPage, Sort.by(Sort.Order.desc("price")))).getContent()
                    : productRepo.findAll(PageRequest.of(page, productsPerPage)).getContent();
        }
        if (sortedByPriceAsc && sortedByPriceDesc || sortedByTitleAsc || sortedByTitleDesc) {
            return sortedByTitleAsc ? productRepo.findAll(PageRequest.of(page, productsPerPage, Sort.by("title"))).getContent()
                    : sortedByTitleDesc ? productRepo.findAll(PageRequest.of(page, productsPerPage, Sort.by(Sort.Order.desc("title")))).getContent()
                    : productRepo.findAll(PageRequest.of(page, productsPerPage)).getContent();
        }
        return sortedByPriceAsc ? productRepo.findAll(PageRequest.of(page, productsPerPage, Sort.by("price"))).getContent()
                : sortedByPriceDesc ? productRepo.findAll(PageRequest.of(page, productsPerPage, Sort.by(Sort.Order.desc("price")))).getContent()
                : productRepo.findAll(PageRequest.of(page, productsPerPage)).getContent();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductException("This product does not exist."));
    }

    public Set<Product> findProducts(String query) {
        return productRepo.findByTitleContainingIgnoreCase(query);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public Product editProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        product.setDescription(updatedProduct.getDescription());
        BigDecimal price = updatedProduct.getPrice();
        if (!price.equals(product.getPrice())) {
            product.setPrice(price);
            cartService.refreshCarts(product, price);
        }
        return product;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void deleteProductById(Long id) {
        Product product = getProductById(id);
        cartService.refreshCarts(product, BigDecimal.ZERO);
        productRepo.deleteById(id);
    }
}

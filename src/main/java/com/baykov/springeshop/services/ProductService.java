package com.baykov.springeshop.services;

import com.baykov.springeshop.exceptions.ProductException;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

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

//    public List<Product> getProducts(ProductFilter filter) {
//        var predicate = QPredicate.builder()
//                .add(filter.getTitle(), product.title::containsIgnoreCase)
//                .add(filter.getStartingPrice(), product.price::gt)
//                .add(filter.getFinalPrice(), product.price::lt)
//                .buildAnd();
//        return productRepo.findAll(predicate);
//    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductException("This product does not exist."));
    }

    public List<Product> findProducts(String query) {
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
        product.setPrice(updatedProduct.getPrice());
        return product;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
    }
}

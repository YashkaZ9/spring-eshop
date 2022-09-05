package com.baykov.springeshop.services;

import com.baykov.springeshop.exceptions.ImageException;
import com.baykov.springeshop.models.Image;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.repos.ImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepo imageRepo;
    private final ProductService productService;

    public Image getImageById(Long productId, Long imageId) {
        Image image = imageRepo.findById(imageId).orElseThrow(() -> new ImageException("This image does not exist."));
        if (!image.getProduct().getId().equals(productId)) {
            throw new ImageException("This product does not have this image.");
        }
        return image;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void saveImage(Long productId, Image image) {
        Product product = productService.getProductById(productId);
        image.setProduct(product);
        product.getImages().add(image);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void removeImages(Long productId) {
        Product product = productService.getProductById(productId);
        product.getImages().clear();
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void removeImage(Long productId, Long imageId) {
        Product product = productService.getProductById(productId);
        Image image = getImageById(productId, imageId);
        product.getImages().remove(image);
    }
}

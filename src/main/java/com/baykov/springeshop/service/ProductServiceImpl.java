package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.ProductDTO;
import com.baykov.springeshop.entity.Cart;
import com.baykov.springeshop.entity.Product;
import com.baykov.springeshop.entity.User;
import com.baykov.springeshop.mapper.ProductMapper;
import com.baykov.springeshop.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, UserService userService, CartService cartService) {
        this.productRepo = productRepo;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepo.findAll();
        return mapper.fromProductsList(products);
    }

    @Override
    @Transactional
    public void addToUserCart(Long productId, String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User '" + username + "' is not found");
        }
        Cart cart = user.getCart();
        if (cart == null) {
            Cart newCart = cartService.createCart(user, Collections.singletonList(productId));
            user.setCart(newCart);
            userService.save(user);
        } else {
            cartService.addProducts(cart, Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        if (productRepo.findByTitle(product.getTitle()) == null) {
            productRepo.save(product);
        }
    }
}

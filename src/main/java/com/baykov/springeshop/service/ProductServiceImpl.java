package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.ProductDTO;
import com.baykov.springeshop.entity.Order;
import com.baykov.springeshop.entity.Product;
import com.baykov.springeshop.entity.User;
import com.baykov.springeshop.mapper.ProductMapper;
import com.baykov.springeshop.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, UserService userService, OrderService orderService) {
        this.productRepo = productRepo;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepo.findAll();
        return mapper.fromProductsList(products);
    }

    @Override
    public Product getOne(Long id) {
        return productRepo.getOne(id);
    }

    @Override
    @Transactional
    public void addToOrder(Long productId, String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User '" + username + "' is not found");
        }
        Order order = orderService.getOrderByCartId(user.getCartId());
        if (order == null) {
            Order newOrder = orderService.createOrder(user, Collections.singletonList(productId));
            userService.addOrder(newOrder);
        } else {
            orderService.addProducts(Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public void addToShop(ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        if (productRepo.findByTitle(product.getTitle()) == null) {
            productRepo.save(product);
        }
    }
}

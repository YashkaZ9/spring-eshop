package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.OrderDTO;
import com.baykov.springeshop.dto.OrderProductsDTO;
import com.baykov.springeshop.entity.*;
import com.baykov.springeshop.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, ProductService productService, UserService userService) {
        this.orderRepo = orderRepo;
        this.productService = productService;
        this.userService = userService;
    }

    public List<OrderProducts> addProductToOrder(Order order, List<Product> products) {
        return products.stream()
                .map(product -> new OrderProducts(order, product, BigDecimal.ONE))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Order createOrder(User user, List<Product> products) {
        Long cartId = user.getCartId();
        if (cartId != null) {
            return user.getOrders().get(cartId.intValue());
        }
        Order order = new Order();
        order.setUser(user);
        List<OrderProducts> orderProducts = addProductToOrder(order, products);
        order.setProducts(orderProducts);
        order.setStatus(OrderStatus.IN_CART);
        user.setCartId(order.getId());
        return orderRepo.save(order);
    }

    @Override
    public Order getOrderByCartId(Long cartId) {
        return orderRepo.getOrderById(cartId);
    }

    @Override
    public Order getOne(Long id) {
        return orderRepo.getOne(id);
    }

    @Override
    @Transactional
    public void addToShop(User user, List<Product> products) {
        Order order = orderRepo.getOrderById(user.getCartId());
        List<OrderProducts> productsList = order.getProducts();
        List<OrderProducts> newProductsList = productsList == null ? new ArrayList<>() : new ArrayList<>(productsList);
        newProductsList.addAll(productsList);
        cart.setProducts(newProductsList);
        cartRepo.save(cart);
    }

    @Override
    @Transactional
    public OrderDTO getCartByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user == null || user.getCart() == null) {
            return new OrderDTO();
        }
        OrderDTO cartDTO = new OrderDTO();
        Map<Long, OrderProductsDTO> productDetails = new HashMap<>();
        List<Product> products = user.getCart().getProducts();
        for (Product product : products) {
            OrderProductsDTO productInfo = productDetails.get(product.getId());
            if (productInfo == null) {
                productDetails.put(product.getId(), new OrderProductsDTO(product));
            } else {
                productInfo.setQuantity(productInfo.getQuantity().add(BigDecimal.ONE));
                productInfo.setSum(productInfo.getSum().add(product.getPrice()));
            }
        }
        cartDTO.setProducts(new ArrayList<>(productDetails.values()));
        cartDTO.aggregate();
        return cartDTO;
    }

    @Override
    public void deleteProductFromCart(Long productId, String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User '" + username + "' is not found");
        }
        cartRepo.deleteById(productId);
    }
}

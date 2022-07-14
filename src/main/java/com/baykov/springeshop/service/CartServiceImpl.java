package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.CartDTO;
import com.baykov.springeshop.dto.CartDetailsDTO;
import com.baykov.springeshop.entity.Cart;
import com.baykov.springeshop.entity.Product;
import com.baykov.springeshop.entity.User;
import com.baykov.springeshop.repo.CartRepo;
import com.baykov.springeshop.repo.ProductRepo;
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
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserService userService;

    @Autowired
    public CartServiceImpl(CartRepo cartRepo, ProductRepo productRepo, UserService userService) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userService = userService;
    }

    private List<Product> transformIdsToProducts(List<Long> productsIds) {
        return productsIds.stream().map(productRepo::getOne).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Cart createCart(User user, List<Long> productsIds) {
        Cart cart = new Cart();
        cart.setUser(user);
        List<Product> products = transformIdsToProducts(productsIds);
        cart.setProducts(products);
        return cartRepo.save(cart);
    }

    @Override
    @Transactional
    public void addProducts(Cart cart, List<Long> productsIds) {
        List<Product> productsList = cart.getProducts();
        List<Product> newProductsList = productsList == null ? new ArrayList<>() : new ArrayList<>(productsList);
        newProductsList.addAll(transformIdsToProducts(productsIds));
        cart.setProducts(newProductsList);
        cartRepo.save(cart);
    }

    @Override
    @Transactional
    public CartDTO getCartByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user == null || user.getCart() == null) {
            return new CartDTO();
        }
        CartDTO cartDTO = new CartDTO();
        Map<Long, CartDetailsDTO> productDetails = new HashMap<>();
        List<Product> products = user.getCart().getProducts();
        for (Product product : products) {
            CartDetailsDTO productInfo = productDetails.get(product.getId());
            if (productInfo == null) {
                productDetails.put(product.getId(), new CartDetailsDTO(product));
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
    public void deleteProduct(Long productId) {
        cartRepo.deleteById(productId);
    }
}

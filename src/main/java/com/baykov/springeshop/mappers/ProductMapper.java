package com.baykov.springeshop.mappers;

import com.baykov.springeshop.dtos.ProductDto;
import com.baykov.springeshop.dtos.UserDto;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);
    Product toProduct(ProductDto productDto);
    ProductDto fromProduct(Product product);
    List<Product> toProducts(List<ProductDto> productsDtos);
    List<ProductDto> fromProducts(List<Product> products);
}

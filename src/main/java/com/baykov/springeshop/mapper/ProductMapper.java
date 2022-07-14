package com.baykov.springeshop.mapper;

import com.baykov.springeshop.dto.ProductDTO;
import com.baykov.springeshop.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);
    Product toProduct(ProductDTO productDTO);
    ProductDTO fromProduct(Product product);
    List<Product> toProductsList(List<ProductDTO> productDTOs);
    List<ProductDTO> fromProductsList(List<Product> products);
}

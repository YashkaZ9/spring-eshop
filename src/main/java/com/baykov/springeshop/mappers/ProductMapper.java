package com.baykov.springeshop.mappers;

import com.baykov.springeshop.dtos.ImageDto;
import com.baykov.springeshop.dtos.ProductDto;
import com.baykov.springeshop.dtos.ProductImagesDto;
import com.baykov.springeshop.models.Image;
import com.baykov.springeshop.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Image toImage(MultipartFile file) throws IOException;

    ImageDto fromImage(Image image) throws IOException;

    Product toProduct(ProductDto productDto);

    ProductDto fromProduct(Product product);

    Set<Product> toProducts(Set<ProductDto> productsDtos);

    Set<ProductDto> fromProducts(Set<Product> products);

    List<Product> toProducts(List<ProductDto> productsDtos);

    List<ProductDto> fromProducts(List<Product> products);

    Product toProductFull(ProductImagesDto productImagesDto);

    ProductImagesDto fromProductFull(Product product);

    Set<Product> toProductsFull(Set<ProductImagesDto> productImagesDtos);

    Set<ProductImagesDto> fromProductsFull(Set<Product> products);
}

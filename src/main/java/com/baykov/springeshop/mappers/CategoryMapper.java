package com.baykov.springeshop.mappers;

import com.baykov.springeshop.dtos.CategoryDto;
import com.baykov.springeshop.dtos.CategoryProductsDto;
import com.baykov.springeshop.dtos.ImageDto;
import com.baykov.springeshop.dtos.ProductDto;
import com.baykov.springeshop.models.Category;
import com.baykov.springeshop.models.Image;
import com.baykov.springeshop.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    Image toImage(MultipartFile file) throws IOException;

    ImageDto fromImage(Image image) throws IOException;

    Product toProduct(ProductDto productDto);

    ProductDto fromProduct(Product product);

    List<Product> toProducts(List<ProductDto> productsDtos);

    List<ProductDto> fromProducts(List<Product> products);

    Category toCategory(CategoryProductsDto categoryProductsDto);

    CategoryProductsDto fromCategoryFull(Category category);

    Category toCategory(CategoryDto categoryDto);

    CategoryDto fromCategory(Category category);

    List<Category> toCategories(List<CategoryDto> categoryDtos);

    List<CategoryDto> fromCategories(List<Category> categories);
}

package com.baykov.springeshop.mappers;

import com.baykov.springeshop.dtos.ImageDto;
import com.baykov.springeshop.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageMapper MAPPER = Mappers.getMapper(ImageMapper.class);

    Image toImage(MultipartFile file) throws IOException;

    ImageDto fromImage(Image image) throws IOException;

    Set<ImageDto> fromImages(Set<Image> images) throws IOException;
}

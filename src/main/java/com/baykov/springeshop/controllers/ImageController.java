package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.ImageDto;
import com.baykov.springeshop.mappers.ImageMapper;
import com.baykov.springeshop.models.Image;
import com.baykov.springeshop.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    @GetMapping("/{productId}/images/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable(value = "productId") Long productId,
                                      @PathVariable(value = "imageId") Long imageId) throws IOException {
        Image image = imageService.getImageById(productId, imageId);
        ImageDto imageDto = imageMapper.fromImage(image);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(imageDto.getContentType()))
                .contentLength(imageDto.getSize())
                .body(imageDto.getBytes());
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<?> addImage(@PathVariable Long productId, @RequestParam(value = "file") MultipartFile file) throws IOException {
        Image image = imageMapper.toImage(file);
        imageService.saveImage(productId, image);
        ImageDto imageDto = imageMapper.fromImage(image);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.valueOf(imageDto.getContentType()))
                .contentLength(imageDto.getSize())
                .body(imageDto.getBytes());
    }

    @DeleteMapping("/{productId}/images")
    public HttpStatus deleteImages(@PathVariable Long productId) {
        imageService.removeImages(productId);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public HttpStatus deleteImage(@PathVariable(value = "productId") Long productId,
                                  @PathVariable(value = "imageId") Long imageId) {
        imageService.removeImage(productId, imageId);
        return HttpStatus.OK;
    }
}

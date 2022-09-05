package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    @NotNull
    private Long id;

    @Size(min = 1, max = 100, message = "Name length should be between 1 and 100 symbols.")
    private String name;

    @Size(min = 1, max = 100, message = "Content type length should be between 1 and 100 symbols.")
    private String contentType;

    @NotNull(message = "Size should be specified.")
    @Min(value = 0, message = "Size should be positive.")
    private Long size;

    @NotNull(message = "Image should be specified.")
    @Lob
    private byte[] bytes;
}

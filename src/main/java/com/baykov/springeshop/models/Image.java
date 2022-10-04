package com.baykov.springeshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "size", "contentType"})
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Size(min = 1, max = 100, message = "Name length should be between 1 and 100 symbols.")
    private String name;

    @NotNull(message = "Size should be specified.")
    @Min(value = 0, message = "Size should be positive.")
    private Long size;

    @Size(min = 1, max = 100, message = "Content type length should be between 1 and 100 symbols.")
    private String contentType;

    @Lob
    private byte[] bytes;
}

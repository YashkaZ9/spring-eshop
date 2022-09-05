package com.baykov.springeshop.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Size(min = 1, max = 100, message = "Name length should be between 1 and 100 symbols.")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Size should be specified.")
    @Min(value = 0, message = "Size should be positive.")
    @Column(name = "size")
    private Long size;

    @Size(min = 1, max = 100, message = "Content type length should be between 1 and 100 symbols.")
    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "bytes")
    private byte[] bytes;
}

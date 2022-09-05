package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto {
    @Pattern(regexp = "[A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, [A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, \\d{6}",
            message = "Address should match the pattern: Country, City, Index (6 numbers)")
    private String address;

    @Size(max = 255, message = "Comment length should be not more than 255 symbols.")
    private String comment;
}

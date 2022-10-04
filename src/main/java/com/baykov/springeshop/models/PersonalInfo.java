package com.baykov.springeshop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 100, message = "Name length should be not more than 100 symbols.")
    private String name;

    @Size(max = 100, message = "Surname length should be not more than 100 symbols.")
    private String surname;

    @Size(max = 100, message = "Phone number should be valid.")
    private String phone;
}

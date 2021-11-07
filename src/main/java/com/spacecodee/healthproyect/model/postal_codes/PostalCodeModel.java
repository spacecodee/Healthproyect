package com.spacecodee.healthproyect.model.postal_codes;

import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostalCodeModel implements Serializable {

    @Getter
    private int idPostalCode;
    @Getter
    @Setter
    private String postalCode;
}

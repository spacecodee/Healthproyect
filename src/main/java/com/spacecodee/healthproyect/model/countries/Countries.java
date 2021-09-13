package com.spacecodee.healthproyect.model.countries;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Countries {

    @Getter
    private Integer id;
    @Getter
    @Setter
    private String country;
}

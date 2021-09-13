package com.spacecodee.healthproyect.model.peoples;

import com.spacecodee.healthproyect.model.countries.Countries;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@ToString
public class Peoples {

    @Getter
    private Integer id;
    @Setter
    @Getter
    private String dni;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String lastname;
    @Setter
    @Getter
    private String mail;
    @Setter
    @Getter
    private String phone;
    @Setter
    @Getter
    private String url_img_profile;
    @Setter
    @Getter
    private Date birthDate;
    @Setter
    @Getter
    private Countries countries;

    @Override
    public int hashCode() {
        return 0;
    }
}

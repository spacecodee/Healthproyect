package com.spacecodee.healthproyect.model.peoples;

import com.spacecodee.healthproyect.model.countries.CountryModel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PeopleModel implements Serializable {

    @Getter
    private Integer idPeople;
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
    private String urlImgProfile;
    @Setter
    @Getter
    private Date birthDate;
    @Setter
    @Getter
    private CountryModel countryModel;

    public PeopleModel(String dni, String name, String lastname, String mail, String phone) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
    }
}

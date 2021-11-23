package com.spacecodee.healthproyect.model.peoples;

import com.spacecodee.healthproyect.model.address.AddressModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PeopleModel implements Serializable {

    @Getter
    @Setter
    private int idPeople;
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
    private String birthDate;
    @Setter
    @Getter
    private AddressModel addressModel;

    public PeopleModel(int idPeople) {
        this.idPeople = idPeople;
    }

    public PeopleModel(String dni, String name, String lastname, String mail, String phone) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
    }

    public PeopleModel(int idPeople, String dni, String name, String lastname, String mail, String phone,
                       AddressModel addressModel) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
        this.addressModel = addressModel;
    }

    public PeopleModel(int idPeople, String dni, String name, String lastname, String mail,
                       String phone, String urlImgProfile, String birthDate) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
        this.urlImgProfile = urlImgProfile;
        this.birthDate = birthDate;
    }

    public PeopleModel(int idPeople, String dni, String name, String lastname, String mail, String phone,
                       String birthDate, AddressModel addressModel) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.addressModel = addressModel;
    }
}

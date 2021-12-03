package com.spacecodee.healthproyect.dto.peoples;

import com.spacecodee.healthproyect.dto.address.AddressDto;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PeopleDto implements Serializable {

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
    private AddressDto addressDto;

    public PeopleDto(int idPeople) {
        this.idPeople = idPeople;
    }

    public PeopleDto(String dni) {
        this.dni = dni;
    }

    public PeopleDto(int idPeople, String dni, String name, String lastname) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
    }

    public PeopleDto(int idPeople, String dni, String name, String lastname, String mail, String phone,
                     AddressDto addressDto) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
        this.addressDto = addressDto;
    }

    public PeopleDto(int idPeople, String dni, String name, String lastname, String mail, String phone,
                     String birthDate, AddressDto addressDto) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.addressDto = addressDto;
    }

    public PeopleDto(int idPeople, String dni, String name, String lastname, String mail, String phone,
                     String birthDate) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public PeopleDto(int idPeople, String dni, String name, String lastname, String mail, String phone) {
        this.idPeople = idPeople;
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.phone = phone;
    }
}

package com.spacecodee.healthproyect.converters.customer;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerTable {

    @Getter
    @Setter
    private int idCustomer;
    @Getter
    @Setter
    private int idPeople;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String dni;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String userName;

    public CustomerTable(String dni, String userName) {
        this.dni = dni;
        this.userName = userName;
    }
}

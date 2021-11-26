package com.spacecodee.healthproyect.dto.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTable {

    @Getter
    @Setter
    private int idUser;
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
    @Getter
    @Setter
    private String userRoleName;
}

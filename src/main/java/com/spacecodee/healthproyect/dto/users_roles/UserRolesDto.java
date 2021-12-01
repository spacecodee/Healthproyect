package com.spacecodee.healthproyect.dto.users_roles;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class UserRolesDto implements Serializable {

    @Getter
    @Setter
    private int idRolUser;

    @Getter
    @Setter
    private String roleName;

    public UserRolesDto(int idRolUser) {
        this.idRolUser = idRolUser;
    }

    public UserRolesDto(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}

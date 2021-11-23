package com.spacecodee.healthproyect.model.users_roles;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class UserRolesModel implements Serializable {

    @Getter
    @Setter
    private int idRolUser;

    @Getter
    @Setter
    private String roleName;

    public UserRolesModel(int idRolUser) {
        this.idRolUser = idRolUser;
    }

    public UserRolesModel(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}

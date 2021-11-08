package com.spacecodee.healthproyect.model.users;

import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import com.spacecodee.healthproyect.model.users_roles.UserRolesModel;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Getter
    private int idCustomer;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private PeopleModel people;
    @Getter
    @Setter
    private UserRolesModel userRolesModel;
}

package com.spacecodee.healthproyect.model.users;

import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import com.spacecodee.healthproyect.model.users_roles.UserRolesModel;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Getter
    private int idUser;
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

    public UserModel(int idUser, String userName, PeopleModel people) {
        this.idUser = idUser;
        this.userName = userName;
        this.people = people;
    }

    public UserModel(int idUser, String userName, PeopleModel people, UserRolesModel userRolesModel) {
        this.idUser = idUser;
        this.userName = userName;
        this.people = people;
        this.userRolesModel = userRolesModel;
    }

    public UserModel(String userName, PeopleModel people) {
        this.userName = userName;
        this.people = people;
    }
}

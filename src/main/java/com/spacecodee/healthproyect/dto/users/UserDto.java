package com.spacecodee.healthproyect.dto.users;

import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.dto.users_roles.UserRolesDto;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

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
    private PeopleDto people;
    @Getter
    @Setter
    private UserRolesDto userRolesDto;

    public UserDto(int idUser) {
        this.idUser = idUser;
    }

    public UserDto(int idUser, String userName, PeopleDto people) {
        this.idUser = idUser;
        this.userName = userName;
        this.people = people;
    }

    public UserDto(int idUser, String userName, PeopleDto people, UserRolesDto userRolesDto) {
        this.idUser = idUser;
        this.userName = userName;
        this.people = people;
        this.userRolesDto = userRolesDto;
    }

    public UserDto(String userName, PeopleDto people) {
        this.userName = userName;
        this.people = people;
    }
}

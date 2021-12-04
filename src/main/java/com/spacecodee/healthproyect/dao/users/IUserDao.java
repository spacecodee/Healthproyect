package com.spacecodee.healthproyect.dao.users;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.users.UserDto;

public interface IUserDao extends ICrudGeneric<UserDto> {

    int total();
}

package com.spacecodee.healthproyect.dao.reserved_days;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.reserved_days.ReservedDaysDto;

public interface IReservedDaysDao extends ICrudGeneric<ReservedDaysDto> {

    int returnMaxId();
}

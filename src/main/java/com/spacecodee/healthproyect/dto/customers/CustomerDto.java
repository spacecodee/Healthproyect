package com.spacecodee.healthproyect.dto.customers;

import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {

    @Getter
    @Setter
    private int idCustomer;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private PeopleDto people;

    public CustomerDto(PeopleDto people) {
        this.people = people;
    }

    public CustomerDto(String userName, PeopleDto people) {
        this.userName = userName;
        this.people = people;
    }

    public CustomerDto(int idCustomer, PeopleDto people) {
        this.idCustomer = idCustomer;
        this.people = people;
    }
}

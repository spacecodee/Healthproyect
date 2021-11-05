package com.spacecodee.healthproyect.model.customers;

import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel implements Serializable {

    @Getter
    private int idCustomer;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private PeopleModel people;

    public CustomerModel(String userName, PeopleModel people) {
        this.userName = userName;
        this.people = people;
    }
}

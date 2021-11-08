package com.spacecodee.healthproyect.dao.users;

import com.spacecodee.healthproyect.model.users.UserModel;
import javafx.collections.ObservableList;

public class UserDaoImpl implements IUserDao{
    @Override
    public ObservableList<UserModel> load() {

        return null;
    }

    @Override
    public ObservableList<UserModel> findByName(String name) {
        return null;
    }

    @Override
    public boolean add(UserModel value) {
        return false;
    }

    @Override
    public boolean update(UserModel value) {
        return false;
    }

    @Override
    public boolean delete(UserModel value) {
        return false;
    }
}

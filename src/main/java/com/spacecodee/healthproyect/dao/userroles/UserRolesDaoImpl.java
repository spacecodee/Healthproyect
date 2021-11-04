package com.spacecodee.healthproyect.dao.userroles;

import com.spacecodee.healthproyect.dao.Conexion;
import com.spacecodee.healthproyect.model.users_roles.UserRolesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRolesDaoImpl implements IUserRolesDao {

    private static final String SQL_LOAD_ROLES = "SELECT id_user_rol, role_name FROM user_roles";

    @Override
    public ObservableList<UserRolesModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<UserRolesModel> roles = FXCollections.observableArrayList();

        try {
            conn = Conexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_LOAD_ROLES);
            rs = pst.executeQuery();

            roles.clear();

            while (rs.next()) {
                var rol = new UserRolesModel(
                        rs.getInt("id_user_rol"),
                        rs.getString("role_name")
                );

                roles.add(rol);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Conexion.close(rs);
            Conexion.close(pst);
            Conexion.close(conn);
        }

        return roles;
    }

    @Override
    public ObservableList<UserRolesModel> findByName(String name) {
        return null;
    }

    @Override
    public ObservableList<UserRolesModel> findById(String id) {
        return null;
    }

    @Override
    public boolean add(UserRolesModel value) {
        return false;
    }

    @Override
    public boolean delete(UserRolesModel value) {
        return false;
    }
}

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
    private static final String SQL_ADD_ROLES = "INSERT INTO user_roles (role_name) VALUES (?)";
    private static final String SQL_UPDATE_ROLES = "UPDATE user_roles SET role_name = ? WHERE id_user_rol = ?";
    private static final String SQL_DELETE_ROLES = "DELETE FROM user_roles WHERE id_user_rol = ?";
    private static final String SQL_FIND_ROLES_BY_NAME = "SELECT id_user_rol, role_name FROM user_roles " +
            "WHERE role_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";

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
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<UserRolesModel> roles = FXCollections.observableArrayList();

        try {
            conn = Conexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_FIND_ROLES_BY_NAME);
            pst.setString(1, name);
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
    public boolean add(UserRolesModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_ADD_ROLES);
            pst.setString(1, value.getRoleName());
            pst.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        } finally {
            assert pst != null;
            Conexion.close(pst);
            Conexion.close(conn);
        }
    }

    @Override
    public boolean update(UserRolesModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_UPDATE_ROLES);
            pst.setString(1, value.getRoleName());
            pst.setInt(2, value.getIdRolUser());
            pst.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        } finally {
            assert pst != null;
            Conexion.close(pst);
            Conexion.close(conn);
        }
    }

    @Override
    public boolean delete(UserRolesModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Conexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_DELETE_ROLES);
            pst.setInt(1, value.getIdRolUser());
            pst.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        } finally {
            assert pst != null;
            Conexion.close(pst);
            Conexion.close(conn);
        }
    }
}

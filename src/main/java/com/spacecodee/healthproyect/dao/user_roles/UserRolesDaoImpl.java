package com.spacecodee.healthproyect.dao.user_roles;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.users_roles.UserRolesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            conn = Connexion.getConnection();
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
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
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
            conn = Connexion.getConnection();
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
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return roles;
    }

    @Override
    public boolean add(UserRolesModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_ADD_ROLES);
            pst.setString(1, value.getRoleName());
            pst.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        } finally {
            assert pst != null;
            Connexion.close(pst);
            Connexion.close(conn);
        }
    }

    @Override
    public boolean update(UserRolesModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
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
            Connexion.close(pst);
            Connexion.close(conn);
        }
    }

    @Override
    public boolean delete(UserRolesModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_DELETE_ROLES);
            pst.setInt(1, value.getIdRolUser());
            pst.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        } finally {
            assert pst != null;
            Connexion.close(pst);
            Connexion.close(conn);
        }
    }

    @Override
    public ArrayList<UserRolesModel> listOfUserRoles() {
        ArrayList<UserRolesModel> listCountries = new ArrayList<>();
        UserRolesModel countryModel;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserRolesDaoImpl.SQL_LOAD_ROLES);
            rs = pst.executeQuery();

            while (rs.next()) {
                countryModel = new UserRolesModel(
                        rs.getInt("id_user_rol"),
                        rs.getString("role_name")
                );

                listCountries.add(countryModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listCountries;
    }
}

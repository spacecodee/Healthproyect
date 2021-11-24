package com.spacecodee.healthproyect.dao.users;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.user.UserTable;
import com.spacecodee.healthproyect.model.users.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements IUserDao {

    private static final String SQL_LOAD_USERS = "SELECT u.id_user,\n" +
            "       u.id_people,\n" +
            "       p.name,\n" +
            "       p.last_name,\n" +
            "       p.mail,\n" +
            "       p.dni,\n" +
            "       p.phone,\n" +
            "       u.user_name,\n" +
            "       ur.role_name\n" +
            "FROM users u\n" +
            "         INNER JOIN peoples p on u.id_people = p.id_people\n" +
            "         INNER JOIN user_roles ur on u.id_user_rol = ur.id_user_rol";
    private static final String SQL_ADD_USER = "INSERT INTO users (user_name, password, id_people, id_user_rol) \n" +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET user_name = ?, password = ?, " +
            "id_people = ?, id_user_rol = ? WHERE id_user = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id_user = ?";
    private static final String SQL_FIND_USERS = "SELECT u.id_user,\n" +
            "       u.id_people,\n" +
            "       p.name,\n" +
            "       p.last_name,\n" +
            "       p.mail,\n" +
            "       p.dni,\n" +
            "       p.phone,\n" +
            "       u.user_name,\n" +
            "       ur.role_name\n" +
            "FROM users u\n" +
            "         INNER JOIN peoples p on u.id_people = p.id_people\n" +
            "         INNER JOIN user_roles ur on u.id_user_rol = ur.id_user_rol\n" +
            "WHERE p.dni LIKE CONCAT('%', ?, '%')\n" +
            "   AND u.user_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";

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
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_ADD_USER);
            this.addValues(value, pst);
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
    public boolean update(UserModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_UPDATE_USER);
            pst.setInt(5, value.getIdUser());
            this.addValues(value, pst);
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

    private void addValues(UserModel value, PreparedStatement pst) throws SQLException {
        pst.setString(1, value.getUserName());
        pst.setString(2, value.getPassword());
        pst.setInt(3, value.getPeople().getIdPeople());
        pst.setInt(4, value.getUserRolesModel().getIdRolUser());
    }

    @Override
    public boolean delete(UserModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_DELETE_USER);
            pst.setInt(1, value.getIdUser());
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
    public ObservableList<UserTable> loadTable() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<UserTable> cities = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_LOAD_USERS);
            rs = pst.executeQuery();

            cities.clear();

            this.returnResults(rs, cities);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return cities;
    }

    @Override
    public ObservableList<UserTable> findByNameAndDniTable(UserTable userModel) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<UserTable> cities = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_FIND_USERS);
            pst.setString(1, userModel.getDni());
            pst.setString(2, userModel.getUserName());
            rs = pst.executeQuery();

            cities.clear();

            this.returnResults(rs, cities);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return cities;
    }

    private void returnResults(ResultSet rs, ObservableList<UserTable> userTables) throws SQLException {
        while (rs.next()) {
            var people = new UserTable(
                    rs.getInt("id_user"),
                    rs.getInt("id_people"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("mail"),
                    rs.getString("dni"),
                    rs.getString("phone"),
                    rs.getString("user_name"),
                    rs.getString("role_name")
            );

            userTables.add(people);
        }
    }
}

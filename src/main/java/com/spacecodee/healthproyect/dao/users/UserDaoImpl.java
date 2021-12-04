package com.spacecodee.healthproyect.dao.users;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.dto.users.UserDto;
import com.spacecodee.healthproyect.dto.users_roles.UserRolesDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private static final String SQL_COUNT_USERS = "SELECT COUNT(id_user) AS total FROM users";
    private static final String SQL_LOGIN = "SELECT u.id_user, u.user_name, u.password, ur.role_name " +
            "FROM users u" +
            "         INNER JOIN peoples p on u.id_people = p.id_people" +
            "         INNER JOIN user_roles ur on u.id_user_rol = ur.id_user_rol " +
            "WHERE u.user_name = ?";

    @Override
    public ArrayList<UserDto> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_LOAD_USERS);
            rs = pst.executeQuery();

            this.returnResults(rs, users);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return users;
    }

    public ArrayList<UserDto> findValue(UserDto value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<UserDto> cities = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_FIND_USERS);
            pst.setString(1, value.getPeople().getDni());
            pst.setString(2, value.getUserName());
            rs = pst.executeQuery();

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
    public boolean add(UserDto value) {
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
    public boolean update(UserDto value) {
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

    @Override
    public boolean delete(UserDto value) {
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
    public UserDto login(UserDto user) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        UserDto userDto = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_LOGIN);
            pst.setString(1, user.getUserName());
            rs = pst.executeQuery();

            if (rs.next()) {
                userDto = new UserDto(
                        rs.getInt("id_user"), rs.getString("user_name"),
                        rs.getString("password"), new UserRolesDto(rs.getString("role_name"))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return userDto;
    }

    @Override
    public int total() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var total = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(UserDaoImpl.SQL_COUNT_USERS);
            rs = pst.executeQuery();

            while (rs.next()) {
                total = rs.getInt("total");
            }

            return total;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return total;
    }

    private void returnResults(ResultSet rs, ArrayList<UserDto> users) throws SQLException {
        while (rs.next()) {
            var user = new UserDto(
                    rs.getInt("id_user"), rs.getString("user_name"),
                    new PeopleDto(
                            rs.getInt("id_people"), rs.getString("dni"),
                            rs.getString("name"), rs.getString("last_name"),
                            rs.getString("mail"), rs.getString("phone")
                    ),
                    new UserRolesDto(rs.getString("role_name"))
            );

            users.add(user);
        }
    }

    private void addValues(UserDto value, PreparedStatement pst) throws SQLException {
        pst.setString(1, value.getUserName());
        pst.setString(2, value.getPassword());
        pst.setInt(3, value.getPeople().getIdPeople());
        pst.setInt(4, value.getUserRolesDto().getIdRolUser());
    }
}

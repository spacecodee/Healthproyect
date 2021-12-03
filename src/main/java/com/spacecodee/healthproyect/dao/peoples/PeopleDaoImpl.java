package com.spacecodee.healthproyect.dao.peoples;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.address.AddressDto;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;

import java.sql.*;
import java.util.ArrayList;

public class PeopleDaoImpl implements IPeopleDao {

    private static final String SQL_LOAD_PEOPLES = "SELECT p.id_people,\n" +
            "       p.dni,\n" +
            "       p.name,\n" +
            "       p.last_name,\n" +
            "       p.mail,\n" +
            "       p.phone,\n" +
            "       c.country_name \n" +
            "FROM peoples p\n" +
            "         INNER JOIN countries c\n" +
            "                    on p.id_address = c.id_country";
    private static final String SQL_ADD_PEOPLE = "INSERT INTO peoples (dni, name, last_name, mail, phone, " +
            "url_img_profile, birth_date, id_address) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PEOPLE = "UPDATE peoples SET dni = ?, name = ?, " +
            " last_name = ?, mail = ?, phone = ? WHERE id_people = ?";
    private static final String SQL_DELETE_PEOPLE = "DELETE FROM peoples WHERE id_people = ?";
    private static final String SQL_FIND_PEOPLE_BY_NAME = "SELECT p.id_people,\n" +
            "       p.dni,\n" +
            "       p.name,\n" +
            "       p.last_name,\n" +
            "       p.mail,\n" +
            "       p.phone,\n" +
            "       c.country_name \n" +
            "FROM peoples p\n" +
            "         INNER JOIN countries c\n" +
            "                    on p.id_address = c.id_country" +
            " WHERE name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_FIND_PEOPLE_BY_DNI = "SELECT p.id_people," +
            "       p.dni," +
            "       p.name," +
            "       p.last_name," +
            "       p.mail," +
            "       p.phone," +
            "       c.country_name " +
            "FROM peoples p" +
            "         INNER JOIN countries c" +
            "                    on p.id_address = c.id_country" +
            " WHERE dni = ?";
    private static final String SQL_MAX_PEOPLE_ID = "SELECT MAX(id_people) AS id FROM peoples";

    @Override
    public ArrayList<PeopleDto> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<PeopleDto> cities = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_LOAD_PEOPLES);
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
    public ArrayList<PeopleDto> findValue(PeopleDto value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<PeopleDto> cities = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_FIND_PEOPLE_BY_NAME);
            pst.setString(1, value.getName());
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
    public boolean add(PeopleDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_ADD_PEOPLE);
            this.addValues(value, pst);
            pst.setString(6, value.getUrlImgProfile());
            pst.setString(7, value.getBirthDate());
            pst.setInt(8, value.getAddressDto().getIdAddress());
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
    public boolean update(PeopleDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_UPDATE_PEOPLE);
            this.addValues(value, pst);
            pst.setInt(6, value.getIdPeople());
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
    public boolean delete(PeopleDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_DELETE_PEOPLE);
            pst.setInt(1, value.getIdPeople());
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
    public PeopleDto findPeopleByDni(String dni) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PeopleDto peopleDto = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_FIND_PEOPLE_BY_DNI);
            pst.setString(1, dni);
            rs = pst.executeQuery();

            while (rs.next()) {
                var address = new AddressDto(
                        rs.getInt("city_name")
                );

                peopleDto = new PeopleDto(
                        rs.getInt("id_people"),
                        rs.getString("dni"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("mail"),
                        rs.getString("phone"),
                        address
                );
            }

            return peopleDto;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return peopleDto;
    }

    @Override
    public int returnMaxId() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var idPeople = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_MAX_PEOPLE_ID);
            rs = pst.executeQuery();

            while (rs.next()) {
                idPeople = rs.getInt("id");
            }

            return idPeople;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return idPeople;
    }

    private void returnResults(ResultSet rs, ArrayList<PeopleDto> peoples) throws SQLException {
        while (rs.next()) {
            var address = new AddressDto(
                    rs.getInt("city_name")
            );

            var people = new PeopleDto(
                    rs.getInt("id_people"),
                    rs.getString("dni"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("mail"),
                    rs.getString("phone"),
                    address
            );

            peoples.add(people);
        }
    }

    private void addValues(PeopleDto value, PreparedStatement pst) throws SQLException {
        pst.setString(1, value.getDni());
        pst.setString(2, value.getName());
        pst.setString(3, value.getLastname());
        pst.setString(4, value.getMail());
        pst.setString(5, value.getPhone());
    }
}

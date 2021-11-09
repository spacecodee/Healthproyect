package com.spacecodee.healthproyect.dao.peoples;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
            " last_name = ?, mail = ?, phone = ?, url_img_profile = ?, birth_date = ?, id_address = ? WHERE id_people = ?";
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
    private static final String SQL_FIND_PEOPLE_DNI = "SELECT id_people,\n" +
            "       dni,\n" +
            "       name,\n" +
            "       last_name,\n" +
            "       mail,\n" +
            "       phone,\n" +
            "       url_img_profile,\n" +
            "       birth_date,\n" +
            "       id_address \n" +
            "FROM peoples \n" +
            "WHERE id_people = ?;";

    @Override
    public ObservableList<PeopleModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<PeopleModel> cities = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_LOAD_PEOPLES);
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
    public ObservableList<PeopleModel> findByName(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<PeopleModel> cities = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_FIND_PEOPLE_BY_NAME);
            pst.setString(1, name);
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

    private void returnResults(ResultSet rs, ObservableList<PeopleModel> peoples) throws SQLException {
        while (rs.next()) {
            var countryModel = new CountryModel(
                    rs.getString("city_name")
            );

            var people = new PeopleModel(
                    rs.getInt("id_people"),
                    rs.getString("dni"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("mail"),
                    rs.getString("phone"),
                    countryModel
            );

            peoples.add(people);
        }
    }

    @Override
    public boolean add(PeopleModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_ADD_PEOPLE);
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
    public boolean update(PeopleModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_UPDATE_PEOPLE);
            this.addValues(value, pst);
            pst.setInt(9, value.getIdPeople());
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

    private void addValues(PeopleModel value, PreparedStatement pst) throws SQLException {
        pst.setString(1, value.getDni());
        pst.setString(2, value.getName());
        pst.setString(3, value.getLastname());
        pst.setString(4, value.getMail());
        pst.setString(5, value.getPhone());
        pst.setString(6, value.getUrlImgProfile());
        pst.setDate(7, (Date) value.getBirthDate());
        pst.setInt(8, value.getCountryModel().getIdCountry());
    }

    @Override
    public boolean delete(PeopleModel value) {
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
    public PeopleModel findPeopleByDni(String dni) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PeopleModel people = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PeopleDaoImpl.SQL_FIND_PEOPLE_DNI);
            pst.setString(1, dni);
            rs = pst.executeQuery();

            people = new PeopleModel(
                    rs.getInt("id_people"),
                    rs.getString("dni"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("mail"),
                    rs.getString("phone"),
                    rs.getString("url_img_profile"),
                    rs.getDate("birth_date")
            );
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return people;
    }
}

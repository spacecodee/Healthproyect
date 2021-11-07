package com.spacecodee.healthproyect.dao.countries;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDaoImpl implements ICountryDao {

    private static final String SQL_LOAD_CITIES = "SELECT c.id_country, c.country_name, c2.city_name \n" +
            " FROM countries c \n" +
            "         INNER JOIN cities c2 \n" +
            "                    on c.id_city = c2.id_city";
    private static final String SQL_ADD_COUNTRY = "INSERT INTO countries (country_name, id_city) VALUES (?, ?)";
    private static final String SQL_UPDATE_COUNTRY = "UPDATE countries SET country_name = ?, id_city = ?" +
            " WHERE id_country = ?";
    private static final String SQL_DELETE_COUNTRY = "DELETE FROM cities WHERE id_city = ?";
    private static final String SQL_FIND_COUNTRIES_BY_CITY_NAME = "SELECT c.id_country, c.country_name, c2.city_name\n" +
            " FROM countries c\n" +
            "         INNER JOIN cities c2\n" +
            " WHERE country_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";

    @Override
    public ObservableList<CountryModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CountryModel> countries = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_LOAD_CITIES);
            rs = pst.executeQuery();

            this.returnResults(rs, countries);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return countries;
    }

    @Override
    public ObservableList<CountryModel> findByName(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CountryModel> countries = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_FIND_COUNTRIES_BY_CITY_NAME);
            pst.setString(1, name);
            rs = pst.executeQuery();

            this.returnResults(rs, countries);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return countries;
    }

    private void returnResults(ResultSet rs, ObservableList<CountryModel> countries) throws SQLException {
        countries.clear();

        while (rs.next()) {
            var cityModel = new CityModel(rs.getString("city_name"));
            var countryModel = new CountryModel(
                    rs.getInt("id_country"),
                    rs.getString("country_name"),
                    cityModel
            );

            countries.add(countryModel);
        }
    }

    @Override
    public boolean add(CountryModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_ADD_COUNTRY);
            pst.setString(1, value.getCountry());
            pst.setInt(2, value.getCityModel().getIdCity());
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
    public boolean update(CountryModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_UPDATE_COUNTRY);
            pst.setString(1, value.getCountry());
            pst.setInt(2, value.getCityModel().getIdCity());
            pst.setInt(3, value.getIdCountry());
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
    public boolean delete(CountryModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_DELETE_COUNTRY);
            pst.setInt(1, value.getIdCountry());
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
}

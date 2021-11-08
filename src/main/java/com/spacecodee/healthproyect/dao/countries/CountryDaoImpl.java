package com.spacecodee.healthproyect.dao.countries;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.countries.CountryTable;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import com.spacecodee.healthproyect.model.postal_codes.PostalCodeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDaoImpl implements ICountryDao {

    private static final String SQL_LOAD_CITIES = "SELECT c.id_country,\n" +
            "       c.country_name,\n" +
            "       c2.id_city,\n" +
            "       c2.city_name,\n" +
            "       d.id_district,\n" +
            "       d.district_name,\n" +
            "       pc.id_postal_code,\n" +
            "       pc.postal_code\n" +
            "FROM countries c\n" +
            "         INNER JOIN cities c2 on c.id_city = c2.id_city\n" +
            "         INNER JOIN districts d on c2.id_district = d.id_district\n" +
            "         INNER JOIN postal_codes pc on c2.id_postal_code = pc.id_postal_code;";
    private static final String SQL_ADD_COUNTRY = "INSERT INTO countries (country_name, id_city) VALUES (?, ?)";
    private static final String SQL_UPDATE_COUNTRY = "UPDATE countries SET country_name = ?, id_city = ?" +
            " WHERE id_country = ?";
    private static final String SQL_DELETE_COUNTRY = "DELETE FROM cities WHERE id_city = ?";
    private static final String SQL_FIND_COUNTRIES_BY_CITY_NAME = "SELECT c.id_country,\n" +
            "       c.country_name,\n" +
            "       c2.id_city,\n" +
            "       c2.city_name,\n" +
            "       d.id_district,\n" +
            "       d.district_name,\n" +
            "       pc.id_postal_code,\n" +
            "       pc.postal_code\n" +
            "FROM countries c\n" +
            "         INNER JOIN cities c2 on c.id_city = c2.id_city\n" +
            "         INNER JOIN districts d on c2.id_district = d.id_district\n" +
            "         INNER JOIN postal_codes pc on c2.id_postal_code = pc.id_postal_code" +
            " WHERE c2.city_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";

    @Override
    public ObservableList<CountryModel> load() {
        return null;
    }

    @Override
    public ObservableList<CountryModel> findByName(String name) {
        return null;
    }

    @Override
    public ObservableList<CountryTable> loadTable() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CountryTable> countries = FXCollections.observableArrayList();

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
    public ObservableList<CountryTable> findByNameTable(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CountryTable> countries = FXCollections.observableArrayList();

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

    private void returnResults(ResultSet rs, ObservableList<CountryTable> countries) throws SQLException {
        countries.clear();

        while (rs.next()) {
            var countryModel = new CountryTable(
                    rs.getInt("id_country"),
                    rs.getString("country_name"),
                    rs.getInt("id_city"),
                    rs.getString("city_name"),
                    rs.getInt("id_postal_code"),
                    rs.getString("postal_code"),
                    rs.getInt("id_district"),
                    rs.getString("district_name")
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

package com.spacecodee.healthproyect.dao.countries;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryDaoImpl implements ICountryDao {

    private static final String SQL_LOAD_COUNTRIES = "SELECT id_country, country_name\n" +
            "FROM countries";
    private static final String SQL_ADD_COUNTRY = "INSERT INTO countries (country_name) VALUES (?)";
    private static final String SQL_UPDATE_COUNTRY = "UPDATE countries SET country_name = ? " +
            " WHERE id_country = ?";
    private static final String SQL_DELETE_COUNTRY = "DELETE FROM cities WHERE id_city = ?";
    private static final String SQL_FIND_COUNTRIES_BY_NAME = "SELECT id_country, country_name\n" +
            "FROM countries " +
            " WHERE country_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_MAX_COUNTRY_ID = "SELECT MAX(id_country) AS id FROM countries";

    @Override
    public ObservableList<CountryModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CountryModel> countries = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_LOAD_COUNTRIES);
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
            pst = conn.prepareStatement(CountryDaoImpl.SQL_FIND_COUNTRIES_BY_NAME);
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

    @Override
    public ArrayList<CountryModel> listOfCountries() {
        ArrayList<CountryModel> listCountries = new ArrayList<>();
        CountryModel countryModel;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_LOAD_COUNTRIES);
            rs = pst.executeQuery();

            while (rs.next()) {
                countryModel = new CountryModel(
                        rs.getInt("id_country"),
                        rs.getString("country_name")
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

    private void returnResults(ResultSet rs, ObservableList<CountryModel> countries) throws SQLException {
        countries.clear();

        while (rs.next()) {
            var countryModel = new CountryModel(
                    rs.getInt("id_country"),
                    rs.getString("country_name")
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
            pst.setString(1, value.getCountryName());
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
            pst.setString(1, value.getCountryName());
            pst.setInt(2, value.getIdCountry());
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

    @Override
    public int getMaxId() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var id = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CountryDaoImpl.SQL_MAX_COUNTRY_ID);
            rs = pst.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return id;
    }
}

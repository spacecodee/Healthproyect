package com.spacecodee.healthproyect.dao.cities;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CityDaoImpl implements ICityDao {

    private static final String SQL_LOAD_CITIES = "SELECT id_city, city_name, id_country FROM cities";
    private static final String SQL_ADD_CITY = "INSERT INTO cities (city_name) VALUES (?);";
    private static final String SQL_UPDATE_CITY = "UPDATE cities SET city_name = ? WHERE id_city = ?";
    private static final String SQL_DELETE_CITY = "DELETE FROM cities WHERE id_city = ?";
    private static final String SQL_FIND_CITY_BY_ID_COUNTRY = "SELECT id_city, city_name, id_country FROM cities" +
            " WHERE id_country  = ?";
    private static final String SQL_MAX_CITY_ID = "SELECT MAX(id_city) AS id FROM cities";

    @Override
    public ObservableList<CityModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CityModel> cities = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_LOAD_CITIES);
            rs = pst.executeQuery();

            cities.clear();

            returnResults(rs, cities);
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
    public ObservableList<CityModel> findByName(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CityModel> cities = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_FIND_CITY_BY_ID_COUNTRY);
            pst.setString(1, name);
            rs = pst.executeQuery();

            cities.clear();

            returnResults(rs, cities);
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

    private void returnResults(ResultSet rs, ObservableList<CityModel> cities) throws SQLException {
        while (rs.next()) {
            var cityModel = new CityModel(
                    rs.getInt("id_city"),
                    rs.getString("city_name"),
                    new CountryModel(rs.getInt("id_country"))
            );

            cities.add(cityModel);
        }
    }

    @Override
    public boolean add(CityModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_ADD_CITY);
            pst.setString(1, value.getName());
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
    public boolean update(CityModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_UPDATE_CITY);
            pst.setString(1, value.getName());
            pst.setInt(2, value.getIdCity());
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
    public boolean delete(CityModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_DELETE_CITY);
            pst.setInt(1, value.getIdCity());
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
    public ArrayList<CityModel> listOfCities() {
        ArrayList<CityModel> listCities = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_LOAD_CITIES);
            rs = pst.executeQuery();

            this.returnResults(rs, listCities);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listCities;
    }

    @Override
    public ArrayList<CityModel> listOfCities(int id) {
        ArrayList<CityModel> listCities = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_FIND_CITY_BY_ID_COUNTRY);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            this.returnResults(rs, listCities);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listCities;
    }

    private void returnResults(ResultSet rs, ArrayList<CityModel> cities) throws SQLException {
        while (rs.next()) {
            var cityModel = new CityModel(
                    rs.getInt("id_city"),
                    rs.getString("city_name"),
                    new CountryModel(rs.getInt("id_country"))
            );

            cities.add(cityModel);
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
            pst = conn.prepareStatement(CityDaoImpl.SQL_MAX_CITY_ID);
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

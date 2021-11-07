package com.spacecodee.healthproyect.dao.cities;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import com.spacecodee.healthproyect.model.postal_codes.PostalCodeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDaoImpl implements ICityDao {

    private static final String SQL_LOAD_CITIES = "SELECT c.id_city,\n" +
            "       c.city_name,\n" +
            "       pc.postal_code,\n" +
            "       d.district_name \n" +
            "FROM cities c\n" +
            "         INNER JOIN postal_codes pc\n" +
            "                    on c.id_postal_code = pc.id_postal_code\n" +
            "         INNER JOIN districts d\n" +
            "                    on c.id_district = d.id_district";
    private static final String SQL_ADD_CITY = "INSERT INTO cities (city_name, id_postal_code, id_district) " +
            "VALUES (?, ?, ?);";
    private static final String SQL_UPDATE_CITY = "UPDATE cities SET city_name = ?, id_postal_code = ?, " +
            " id_district = ? WHERE id_city = ?";
    private static final String SQL_DELETE_CITY = "DELETE FROM cities WHERE id_city = ?";
    private static final String SQL_FIND_CITY_BY_CITY_NAME = "SELECT c.id_city,\n" +
            "       c.city_name,\n" +
            "       pc.postal_code,\n" +
            "       d.district_name \n" +
            "FROM cities c\n" +
            "         INNER JOIN postal_codes pc\n" +
            "                    on c.id_postal_code = pc.id_postal_code\n" +
            "         INNER JOIN districts d\n" +
            "                    on c.id_district = d.id_district" +
            " WHERE district_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";

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
            pst = conn.prepareStatement(CityDaoImpl.SQL_FIND_CITY_BY_CITY_NAME);
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
            var postalCodeModel = new PostalCodeModel(
                    rs.getString("postal_code")
            );

            var districtModel = new DistrictModel(
                    rs.getString("district_name")
            );

            var cityModel = new CityModel(
                    rs.getInt("id_city"),
                    rs.getString("city_name"),
                    postalCodeModel,
                    districtModel
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
            pst.setInt(2, value.getPostalCodeModel().getIdPostalCode());
            pst.setInt(3, value.getDistrictModel().getIdDistrict());
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
            pst.setInt(2, value.getPostalCodeModel().getIdPostalCode());
            pst.setInt(3, value.getDistrictModel().getIdDistrict());
            pst.setInt(4, value.getIdCity());
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
}

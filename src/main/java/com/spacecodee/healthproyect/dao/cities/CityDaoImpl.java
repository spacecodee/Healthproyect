package com.spacecodee.healthproyect.dao.cities;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.cities.CityDto;
import com.spacecodee.healthproyect.dto.countries.CountryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CityDaoImpl implements ICityDao {

    private static final String SQL_LOAD_CITIES = "SELECT c.id_city, c.city_name, c2.id_country, c2.country_name \n" +
            "FROM cities c\n" +
            "         INNER JOIN countries c2 on c.id_country = c2.id_country";
    private static final String SQL_ADD_CITY = "INSERT INTO cities (city_name, id_country) VALUES (?, ?);";
    private static final String SQL_UPDATE_CITY = "UPDATE cities SET city_name = ?, id_country = ? WHERE id_city = ?";
    private static final String SQL_DELETE_CITY = "DELETE FROM cities WHERE id_city = ?";
    private static final String SQL_FIND_CITY_BY_ID_COUNTRY = "SELECT id_city, city_name, id_country FROM cities" +
            " WHERE id_country  = ?";
    private static final String SQL_FIND_CITY_BY_CITY_NAME = "SELECT c.id_city, c.city_name, c2.id_country, c2.country_name\n" +
            "FROM cities c\n" +
            "         INNER JOIN countries c2 on c.id_country = c2.id_country " +
            "WHERE c.city_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_MAX_CITY_ID = "SELECT MAX(id_city) AS id FROM cities";

    @Override
    public ArrayList<CityDto> load() {
        ArrayList<CityDto> listCities = new ArrayList<>();

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

    public ArrayList<CityDto> findValue(CityDto value) {
        ArrayList<CityDto> listCities = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_FIND_CITY_BY_CITY_NAME);
            pst.setString(1, value.getCityName());
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
    public boolean add(CityDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_ADD_CITY);
            pst.setString(1, value.getCityName());
            pst.setInt(2, value.getCountryDto().getIdCountry());
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
    public boolean update(CityDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_UPDATE_CITY);
            pst.setString(1, value.getCityName());
            pst.setInt(2, value.getCountryDto().getIdCountry());
            pst.setInt(3, value.getIdCity());
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
    public boolean delete(CityDto value) {
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
    public ArrayList<CityDto> listOfCities(int id) {
        ArrayList<CityDto> listCities = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CityDaoImpl.SQL_FIND_CITY_BY_ID_COUNTRY);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                var cityModel = new CityDto(
                        rs.getInt("id_city"),
                        rs.getString("city_name"),
                        new CountryDto(rs.getInt("id_country"))
                );

                listCities.add(cityModel);
            }

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

    private void returnResults(ResultSet rs, ArrayList<CityDto> cities) throws SQLException {
        while (rs.next()) {
            var cityModel = new CityDto(
                    rs.getInt("id_city"),
                    rs.getString("city_name"),
                    new CountryDto(rs.getInt("id_country"), rs.getString("country_name"))
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

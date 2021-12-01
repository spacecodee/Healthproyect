package com.spacecodee.healthproyect.dao.districs;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.cities.CityDto;
import com.spacecodee.healthproyect.dto.countries.CountryDto;
import com.spacecodee.healthproyect.dto.districts.DistrictDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DistrictDaoImpl implements IDistrictDao {

    private static final String SQL_LOAD_DISTRICTS = "SELECT d.id_district, d.district_name, c.id_city, " +
            "c.city_name, c2.id_country, c2.country_name\n" +
            "FROM districts d\n" +
            "         INNER JOIN cities c on d.id_city = c.id_city\n" +
            "         INNER JOIN countries c2 on c.id_country = c2.id_country";
    private static final String SQL_ADD_DISTRICT = "INSERT INTO districts (district_name, id_city) VALUES (?, ?)";
    private static final String SQL_UPDATE_DISTRICT = "UPDATE districts SET district_name = ?, id_city = ? WHERE id_district = ?";
    private static final String SQL_DELETE_DISTRICT = "DELETE FROM districts WHERE id_district = ?";
    private static final String SQL_FIND_DISTRICT_BY_ID_CITY = "SELECT id_district, district_name, id_city\n" +
            "FROM districts WHERE id_city  = ?";
    private static final String SQL_FIND_DISTRICT_BY_DISTRICT_NAME = "SELECT d.id_district, d.district_name, c.id_city, " +
            "c.city_name, c2.id_country, c2.country_name\n" +
            "FROM districts d\n" +
            "         INNER JOIN cities c on d.id_city = c.id_city\n" +
            "         INNER JOIN countries c2 on c.id_country = c2.id_country " +
            "WHERE d.district_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_MAX_DISTRICT_ID = "SELECT MAX(id_district) AS id FROM districts";

    @Override
    public ArrayList<DistrictDto> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<DistrictDto> listDistricts = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_LOAD_DISTRICTS);
            rs = pst.executeQuery();

            this.returnResults(rs, listDistricts);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listDistricts;
    }

    public ArrayList<DistrictDto> findValue(DistrictDto value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<DistrictDto> listDistricts = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_FIND_DISTRICT_BY_DISTRICT_NAME);
            pst.setString(1, value.getDistrictName());
            rs = pst.executeQuery();

            this.returnResults(rs, listDistricts);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listDistricts;
    }

    @Override
    public boolean add(DistrictDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_ADD_DISTRICT);
            pst.setString(1, value.getDistrictName());
            pst.setInt(2, value.getCityDto().getIdCity());
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
    public boolean update(DistrictDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_UPDATE_DISTRICT);
            pst.setString(1, value.getDistrictName());
            pst.setInt(2, value.getCityDto().getIdCity());
            pst.setInt(3, value.getIdDistrict());
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
    public boolean delete(DistrictDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_DELETE_DISTRICT);
            pst.setInt(1, value.getIdDistrict());
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
    public ArrayList<DistrictDto> listOfDistrict(int id) {
        ArrayList<DistrictDto> listDistricts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_FIND_DISTRICT_BY_ID_CITY);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                var districtModel = new DistrictDto(
                        rs.getInt("id_district"),
                        rs.getString("district_name"),
                        new CityDto(rs.getInt("id_city")));

                listDistricts.add(districtModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listDistricts;
    }

    @Override
    public int getMaxId() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var id = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_MAX_DISTRICT_ID);
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

    private void returnResults(ResultSet rs, ArrayList<DistrictDto> districts) throws SQLException {
        while (rs.next()) {
            var districtModel = new DistrictDto(
                    rs.getInt("id_district"),
                    rs.getString("district_name"),
                    new CityDto(rs.getInt("id_city"), rs.getString("city_name"),
                            new CountryDto(rs.getInt("id_country"), rs.getString("country_name"))
                    ));

            districts.add(districtModel);
        }
    }
}

package com.spacecodee.healthproyect.dao.districs;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DistrictDaoImpl implements IDistrictDao {

    private static final String SQL_LOAD_DISTRICTS = "SELECT id_district, district_name, id_city FROM districts";
    private static final String SQL_ADD_DISTRICT = "INSERT INTO districts (district_name) VALUES (?)";
    private static final String SQL_UPDATE_DISTRICT = "UPDATE districts SET district_name = ? WHERE id_district = ?";
    private static final String SQL_DELETE_DISTRICT = "DELETE FROM districts WHERE id_district = ?";
    private static final String SQL_FIND_DISTRICT_BY_ID_CITY = "SELECT id_district, district_name, id_city\n" +
            "FROM districts WHERE id_city  = ?";
    private static final String SQL_MAX_DISTRICT_ID = "SELECT MAX(id_district) AS id FROM districts";

    @Override
    public ObservableList<DistrictModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<DistrictModel> districts = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_LOAD_DISTRICTS);
            rs = pst.executeQuery();

            districts.clear();

            this.returnResults(rs, districts);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return districts;
    }

    @Override
    public ObservableList<DistrictModel> findByName(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<DistrictModel> districts = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_FIND_DISTRICT_BY_ID_CITY);
            pst.setString(1, name);
            rs = pst.executeQuery();

            districts.clear();

            this.returnResults(rs, districts);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return districts;
    }

    private void returnResults(ResultSet rs, ObservableList<DistrictModel> districts) throws SQLException {
        while (rs.next()) {
            var districtModel = new DistrictModel(
                    rs.getInt("id_district"),
                    rs.getString("district_name"),
                    new CityModel("id_city")
            );

            districts.add(districtModel);
        }
    }

    @Override
    public boolean add(DistrictModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_ADD_DISTRICT);
            pst.setString(1, value.getDistrictName());
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
    public boolean update(DistrictModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_UPDATE_DISTRICT);
            pst.setString(1, value.getDistrictName());
            pst.setInt(2, value.getIdDistrict());
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
    public boolean delete(DistrictModel value) {
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
    public ArrayList<DistrictModel> listOfDistrict() {
        ArrayList<DistrictModel> listDistricts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

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

    @Override
    public ArrayList<DistrictModel> listOfDistrict(int id) {
        ArrayList<DistrictModel> listDistricts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_FIND_DISTRICT_BY_ID_CITY);
            pst.setInt(1, id);
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

    private void returnResults(ResultSet rs, ArrayList<DistrictModel> districts) throws SQLException {
        while (rs.next()) {
            var districtModel = new DistrictModel(
                    rs.getInt("id_district"),
                    rs.getString("district_name"),
                    new CityModel("id_city")
            );

            districts.add(districtModel);
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
}

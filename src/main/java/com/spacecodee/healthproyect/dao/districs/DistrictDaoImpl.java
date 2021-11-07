package com.spacecodee.healthproyect.dao.districs;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DistrictDaoImpl implements IDistrictDao {

    private static final String SQL_LOAD_DISTRICTS = "SELECT id_district, district_name FROM districts";
    private static final String SQL_ADD_DISTRICT = "INSERT INTO districts (district_name) VALUES (?)";
    private static final String SQL_UPDATE_DISTRICT = "UPDATE districts SET district_name = ? WHERE id_district = ?";
    private static final String SQL_DELETE_DISTRICT = "DELETE FROM districts WHERE id_district = ?";
    private static final String SQL_FIND_DISTRICT_BY_NAME = "SELECT id_district, district_name FROM districts " +
            "WHERE district_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";

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

            while (rs.next()) {
                var districtModel = new DistrictModel(
                        rs.getInt("id_district"),
                        rs.getString("name")
                );

                districts.add(districtModel);
            }
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
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_FIND_DISTRICT_BY_NAME);
            pst.setString(1, name);
            rs = pst.executeQuery();

            districts.clear();

            while (rs.next()) {
                var districtModel = new DistrictModel(
                        rs.getInt("id_district"),
                        rs.getString("name")
                );

                districts.add(districtModel);
            }
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
    public boolean add(DistrictModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(DistrictDaoImpl.SQL_ADD_DISTRICT);
            pst.setString(1, value.getDistrict_name());
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
            pst.setString(1, value.getDistrict_name());
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
}

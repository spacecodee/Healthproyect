package com.spacecodee.healthproyect.dao.postal_code;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.postal_codes.PostalCodeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostalCodeDaoImpl implements IPostalCodeDao {

    private static final String SQL_LOAD_POSTAL_CODES = "SELECT id_postal_code, postal_code FROM postal_codes";
    private static final String SQL_ADD_POSTAL_CODE = "INSERT INTO postal_codes (postal_code) VALUES (?)";
    private static final String SQL_UPDATE_POSTAL_CODE = "UPDATE postal_codes SET postal_code = ? WHERE id_postal_code = ?";
    private static final String SQL_DELETE_POSTAL_CODE = "DELETE FROM postal_codes WHERE id_postal_code = ?";
    private static final String SQL_FIND_POSTAL_CODE_BY_POSTAL_CODE = "SELECT id_postal_code, postal_code " +
            "FROM postal_codes WHERE postal_code COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_MAX_POSTAL_CODE_ID = "SELECT MAX(id_postal_code) AS id FROM postal_codes";

    @Override
    public ObservableList<PostalCodeModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<PostalCodeModel> postalCodes = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PostalCodeDaoImpl.SQL_LOAD_POSTAL_CODES);
            rs = pst.executeQuery();

            postalCodes.clear();

            while (rs.next()) {
                var postalCodeModel = new PostalCodeModel(
                        rs.getInt("id_postal_code"),
                        rs.getString("postal_code")
                );

                postalCodes.add(postalCodeModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return postalCodes;
    }

    @Override
    public ObservableList<PostalCodeModel> findByName(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<PostalCodeModel> postalCodes = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PostalCodeDaoImpl.SQL_FIND_POSTAL_CODE_BY_POSTAL_CODE);
            pst.setString(1, name);
            rs = pst.executeQuery();

            postalCodes.clear();

            while (rs.next()) {
                var postalCodeModel = new PostalCodeModel(
                        rs.getInt("id_postal_code"),
                        rs.getString("postal_code")
                );

                postalCodes.add(postalCodeModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return postalCodes;
    }

    @Override
    public boolean add(PostalCodeModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PostalCodeDaoImpl.SQL_ADD_POSTAL_CODE);
            pst.setString(1, value.getPostalCode());
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
    public boolean update(PostalCodeModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PostalCodeDaoImpl.SQL_UPDATE_POSTAL_CODE);
            pst.setString(1, value.getPostalCode());
            pst.setInt(2, value.getIdPostalCode());
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
    public boolean delete(PostalCodeModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(PostalCodeDaoImpl.SQL_DELETE_POSTAL_CODE);
            pst.setInt(1, value.getIdPostalCode());
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
            pst = conn.prepareStatement(PostalCodeDaoImpl.SQL_MAX_POSTAL_CODE_ID);
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

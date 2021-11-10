package com.spacecodee.healthproyect.dao.address;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.address.AddressTable;
import com.spacecodee.healthproyect.model.address.AddressModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDaoImpl implements IAddressDao {

    private static final String SQL_LOAD_ADDRESS = "SELECT a.id_address, c2.id_country, c2.country_name, c.id_city, " +
            "c.city_name, d.id_district, d.district_name \n" +
            "FROM address a\n" +
            "         INNER JOIN countries c2 on a.id_country = c2.id_country\n" +
            "         INNER JOIN cities c on a.id_city = c.id_city\n" +
            "         INNER JOIN districts d on a.id_district = d.id_district";
    private static final String SQL_ADD_ADDRESS = "INSERT INTO address (id_country, id_city, id_district) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_ADDRESS = "UPDATE address SET id_country = ?, id_city = ?, id_district = ? " +
            " WHERE id_address = ?";
    private static final String SQL_DELETE_ADDRESS = "DELETE FROM address WHERE id_address = ?";
    private static final String SQL_FIND_ADDRESS_BY_CITY_NAME = "SELECT a.id_address, c2.id_country, c2.country_name, c.id_city, " +
            "c.city_name, d.id_district, d.district_name \n" +
            "FROM address a\n" +
            "         INNER JOIN countries c2 on a.id_country = c2.id_country\n" +
            "         INNER JOIN cities c on a.id_city = c.id_city\n" +
            "         INNER JOIN districts d on a.id_district = d.id_district" +
            " WHERE c.city_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_MAX_COUNTRY_ID = "SELECT MAX(id_country) AS id FROM countries";

    @Override
    public ObservableList<AddressModel> load() {
        return null;
    }

    @Override
    public ObservableList<AddressModel> findByName(String name) {
        return null;
    }

    @Override
    public ObservableList<AddressTable> loadTable() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<AddressTable> countries = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_LOAD_ADDRESS);
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
    public ObservableList<AddressTable> findByNameTable(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<AddressTable> countries = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_FIND_ADDRESS_BY_CITY_NAME);
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

    private void returnResults(ResultSet rs, ObservableList<AddressTable> address) throws SQLException {
        address.clear();

        while (rs.next()) {
            var addressTable = new AddressTable(
                    rs.getInt("id_address"),
                    rs.getInt("id_country"),
                    rs.getString("country_name"),
                    rs.getInt("id_city"),
                    rs.getString("city_name"),
                    rs.getInt("id_district"),
                    rs.getString("district_name")
            );

            address.add(addressTable);
        }
    }

    @Override
    public boolean add(AddressModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_ADD_ADDRESS);
            pst.setInt(1, value.getCountryModel().getIdCountry());
            pst.setInt(2, value.getCityModel().getIdCity());
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
    public boolean update(AddressModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_UPDATE_ADDRESS);
            pst.setInt(1, value.getCountryModel().getIdCountry());
            pst.setInt(2, value.getCityModel().getIdCity());
            pst.setInt(3, value.getDistrictModel().getIdDistrict());
            pst.setInt(4, value.getIdAddress());
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
    public boolean delete(AddressModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_DELETE_ADDRESS);
            pst.setInt(1, value.getIdAddress());
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

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
import java.util.ArrayList;

public class AddressDaoImpl implements IAddressDao {

    private static final String SQL_LOAD_ADDRESS = "SELECT a.id_address, c2.country_name, " +
            "c.city_name, d.district_name, a.address\n" +
            "FROM address a\n" +
            "         INNER JOIN districts d on a.id_district = d.id_district\n" +
            "         INNER JOIN cities c on d.id_city = c.id_city\n" +
            "         INNER JOIN countries c2 on c.id_country = c2.id_country";
    private static final String SQL_ADD_ADDRESS = "INSERT INTO address (address, id_district) VALUES (?, ?)";
    private static final String SQL_UPDATE_ADDRESS = "UPDATE address SET address = ?, id_district = ? " +
            " WHERE id_address = ?";
    private static final String SQL_DELETE_ADDRESS = "DELETE FROM address WHERE id_address = ?";
    private static final String SQL_FIND_ADDRESS_BY_CITY_NAME = "SELECT a.id_address, c2.country_name, " +
            "c.city_name, d.district_name, a.address\n" +
            "FROM address a\n" +
            "         INNER JOIN districts d on a.id_district = d.id_district\n" +
            "         INNER JOIN cities c on d.id_city = c.id_city\n" +
            "         INNER JOIN countries c2 on c.id_country = c2.id_country" +
            " WHERE c.city_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%') " +
            "AND d.district_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_MAX_ADDRESS_ID = "SELECT MAX(id_address) AS id FROM address";

    @Override
    public ArrayList<AddressModel> load() {
        return null;
    }

    public ArrayList<AddressModel> findValue(AddressModel name) {
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
    public ObservableList<AddressTable> findByNameTable(AddressTable addressModel) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<AddressTable> countries = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_FIND_ADDRESS_BY_CITY_NAME);
            pst.setString(1, addressModel.getCityName());
            pst.setString(2, addressModel.getDistrictName());
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
    public int returnMaxId() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var idAddress = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_MAX_ADDRESS_ID);
            rs = pst.executeQuery();
            while (rs.next()) {
                idAddress = rs.getInt("id");
            }

            return idAddress;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return idAddress;
    }

    private void returnResults(ResultSet rs, ObservableList<AddressTable> address) throws SQLException {
        address.clear();

        while (rs.next()) {
            var addressTable = new AddressTable(
                    rs.getInt("id_address"),
                    rs.getString("country_name"),
                    rs.getString("city_name"),
                    rs.getString("district_name"),
                    rs.getString("address")
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
            pst.setString(1, value.getAddressName());
            pst.setInt(2, value.getDistrictModel().getIdDistrict());
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
            pst.setString(1, value.getAddressName());
            pst.setInt(2, value.getDistrictModel().getIdDistrict());
            pst.setInt(3, value.getIdAddress());
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

package com.spacecodee.healthproyect.dao.address;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.address.AddressDto;
import com.spacecodee.healthproyect.dto.cities.CityDto;
import com.spacecodee.healthproyect.dto.countries.CountryDto;
import com.spacecodee.healthproyect.dto.districts.DistrictDto;

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
    public ArrayList<AddressDto> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<AddressDto> countries = new ArrayList<>();

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

    public ArrayList<AddressDto> findValue(AddressDto value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<AddressDto> countries = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_FIND_ADDRESS_BY_CITY_NAME);
            pst.setString(1, value.getDistrictDto().getCityDto().getCityName());
            pst.setString(2, value.getDistrictDto().getDistrictName());
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

    @Override
    public boolean add(AddressDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_ADD_ADDRESS);
            pst.setString(1, value.getAddressName());
            pst.setInt(2, value.getDistrictDto().getIdDistrict());
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
    public boolean update(AddressDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(AddressDaoImpl.SQL_UPDATE_ADDRESS);
            pst.setString(1, value.getAddressName());
            pst.setInt(2, value.getDistrictDto().getIdDistrict());
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
    public boolean delete(AddressDto value) {
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

    private void returnResults(ResultSet rs, ArrayList<AddressDto> address) throws SQLException {
        address.clear();

        while (rs.next()) {
            var addressM = new AddressDto(
                    rs.getInt("id_address"), rs.getString("address"),
                    new DistrictDto(rs.getString("district_name"),
                                    new CityDto(rs.getString("city_name"),
                                                new CountryDto(rs.getString("country_name"))))
            );

            address.add(addressM);
        }
    }
}

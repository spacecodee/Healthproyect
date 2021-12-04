package com.spacecodee.healthproyect.dao.type_reservations;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dao.users.UserDaoImpl;
import com.spacecodee.healthproyect.dto.type_reservations.TypeReservationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TypeReservationsDaoImpl implements ITypeReservationsDao {

    private static final String SQL_LOAD_TYPE_RESERVATION_AP = "SELECT id_type_reservation, name, price " +
            "FROM type_reservations";
    private static final String SQL_FIND_TYPE_RESERVATION_AP = "SELECT id_type_reservation, name, price " +
            "FROM type_reservations " +
            "WHERE name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";
    private static final String SQL_ADD_TYPE_RESERVATION_AP = "INSERT INTO type_reservations (name, price) " +
            "VALUES (?, ?)";
    private static final String SQL_UPDATE_TYPE_RESERVATION_AP = "UPDATE type_reservations " +
            "SET name  = ?, " +
            "    price = ? " +
            "WHERE id_type_reservation = ?";
    private static final String SQL_DELETE_TYPE_RESERVATION_AP = "DELETE " +
            "FROM type_reservations " +
            "WHERE id_type_reservation = ?";
    private static final String SQL_MAX_TYPE_RESERVATIONS_ID = "SELECT MAX(id_type_reservation) AS id FROM type_reservations";
    private static final String SQL_COUNT_TYPE_RESERVATIONS = "SELECT COUNT(id_type_reservation) AS total FROM type_reservations";

    @Override
    public ArrayList<TypeReservationDto> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<TypeReservationDto> typeReservationList = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_LOAD_TYPE_RESERVATION_AP);
            rs = pst.executeQuery();

            returnResults(rs, typeReservationList);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return typeReservationList;
    }

    public ArrayList<TypeReservationDto> findValue(TypeReservationDto value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<TypeReservationDto> typeReservationList = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_FIND_TYPE_RESERVATION_AP);
            pst.setString(1, value.getNameReservation());
            rs = pst.executeQuery();

            returnResults(rs, typeReservationList);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return typeReservationList;
    }

    @Override
    public boolean add(TypeReservationDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_ADD_TYPE_RESERVATION_AP);
            pst.setString(1, value.getNameReservation());
            pst.setDouble(2, value.getPriceReservation());
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
    public boolean update(TypeReservationDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_UPDATE_TYPE_RESERVATION_AP);
            pst.setString(1, value.getNameReservation());
            pst.setDouble(2, value.getPriceReservation());
            pst.setInt(3, value.getIdTypeReservation());
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
    public boolean delete(TypeReservationDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_DELETE_TYPE_RESERVATION_AP);
            pst.setInt(1, value.getIdTypeReservation());
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

    private void returnResults(ResultSet rs, ArrayList<TypeReservationDto> typeReservationList) throws SQLException {
        while (rs.next()) {
            var typeReservationModel = new TypeReservationDto(
                    rs.getInt("id_type_reservation"),
                    rs.getString("name"),
                    rs.getDouble("price")
            );

            typeReservationList.add(typeReservationModel);
        }
    }

    @Override
    public int total() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var total = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_COUNT_TYPE_RESERVATIONS);
            rs = pst.executeQuery();

            while (rs.next()) {
                total = rs.getInt("total");
            }

            return total;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return total;
    }

    @Override
    public int returnMaxId() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var idPeople = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_MAX_TYPE_RESERVATIONS_ID);
            rs = pst.executeQuery();

            while (rs.next()) {
                idPeople = rs.getInt("id");
            }

            return idPeople;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return idPeople;
    }
}

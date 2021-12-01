package com.spacecodee.healthproyect.dao.reserved_days;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.reserved_days.ReservedDaysDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservedDaysDaoImpl implements IReservedDaysDao {

    private static final String SQL_ADD_RESERVED_DAY_AP = "INSERT INTO reserved_days (reservation_date) " +
            "VALUES (?)";
    private static final String SQL_UPDATE_RESERVED_DAY_AP = "UPDATE reserved_days " +
            "SET reservation_date  = ? " +
            "WHERE id_reserved_day = ?";
    private static final String SQL_DELETE_RESERVED_DAY_AP = "DELETE " +
            "FROM reserved_days " +
            "WHERE id_reserved_day = ?";
    private static final String SQL_MAX_RESERVED_DAYS_ID = "SELECT MAX(id_reserved_day) AS id FROM reserved_days";

    @Override
    public ArrayList<ReservedDaysDto> load() {
        return null;
    }

    @Override
    public ArrayList<ReservedDaysDto> findValue(ReservedDaysDto value) {
        return null;
    }

    @Override
    public boolean add(ReservedDaysDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservedDaysDaoImpl.SQL_ADD_RESERVED_DAY_AP);
            pst.setString(1, value.getReservationDate());
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
    public boolean update(ReservedDaysDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservedDaysDaoImpl.SQL_UPDATE_RESERVED_DAY_AP);
            pst.setString(1, value.getReservationDate());
            pst.setDouble(2, value.getIdReservedDay());
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
    public boolean delete(ReservedDaysDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservedDaysDaoImpl.SQL_DELETE_RESERVED_DAY_AP);
            pst.setInt(1, value.getIdReservedDay());
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
    public int returnMaxId() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var idPeople = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservedDaysDaoImpl.SQL_MAX_RESERVED_DAYS_ID);
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

package com.spacecodee.healthproyect.dao.reservation_appointments;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.reservation_appointments.ReservationTable;
import com.spacecodee.healthproyect.model.reservation_appointments.ReservationApModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationApDaoImpl implements IReservationApDao {

    private static final String SQL_LOAD_RESERVATION_AP = "SELECT r.id_reservation_quote,\n" +
            "       r.id_customer,\n" +
            "       r.id_user,\n" +
            "       r.id_type_reservations,\n" +
            "       r.id_reserved_day,\n" +
            "       p.name      AS customer_name,\n" +
            "       p.last_name AS customer_last_name,\n" +
            "       p.dni       AS customer_dni,\n" +
            "       tr.name,\n" +
            "       tr.price,\n" +
            "       rd.reservation_date,\n" +
            "       u.user_name,\n" +
            "       pu.dni      AS user_dni\n" +
            "FROM reservation_appointments r\n" +
            "         INNER JOIN customers c on r.id_customer = c.id_customer\n" +
            "         INNER JOIN users u on r.id_user = u.id_user\n" +
            "         INNER JOIN peoples p on c.id_people = p.id_people\n" +
            "         INNER JOIN peoples pu on u.id_people = pu.id_people\n" +
            "         INNER JOIN type_reservations tr on r.id_type_reservations = tr.id_type_reservation\n" +
            "         INNER JOIN reserved_days rd on r.id_reserved_day = rd.id_reserved_day";
    private static final String SQL_FIND_RESERVATION_AP = "SELECT r.id_reservation_quote,\n" +
            "       r.id_customer,\n" +
            "       r.id_user,\n" +
            "       r.id_type_reservations,\n" +
            "       r.id_reserved_day,\n" +
            "       p.name      AS customer_name,\n" +
            "       p.last_name AS customer_last_name,\n" +
            "       p.dni       AS customer_dni,\n" +
            "       tr.name,\n" +
            "       tr.price,\n" +
            "       rd.reservation_date,\n" +
            "       u.user_name,\n" +
            "       pu.dni      AS user_dni\n" +
            "FROM reservation_appointments r\n" +
            "         INNER JOIN customers c on r.id_customer = c.id_customer\n" +
            "         INNER JOIN users u on r.id_user = u.id_user\n" +
            "         INNER JOIN peoples p on c.id_people = p.id_people\n" +
            "         INNER JOIN peoples pu on u.id_people = pu.id_people\n" +
            "         INNER JOIN type_reservations tr on r.id_type_reservations = tr.id_type_reservation\n" +
            "         INNER JOIN reserved_days rd on r.id_reserved_day = rd.id_reserved_day " +
            "WHERE p.dni LIKE CONCAT('%', ?, '%')";
    private static final String SQL_ADD_RESERVATION = "INSERT INTO reservation_appointments (id_customer, id_user," +
            "id_type_reservations, id_reserved_day)\n" +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_RESERVATION = "UPDATE reservation_appointments\n" +
            "SET id_customer          = ?,\n" +
            "    id_user              = ?,\n" +
            "    id_type_reservations = ?,\n" +
            "    id_reserved_day      = ?\n" +
            "WHERE id_reservation_quote = ?";
    private static final String SQL_DELETE_RESERVATION = "DELETE FROM reservation_appointments\n" +
            "WHERE id_reservation_quote = ?";

    @Override
    public ObservableList<ReservationApModel> load() {
        return null;
    }

    @Override
    public ObservableList<ReservationApModel> findByName(String name) {
        return null;
    }

    @Override
    public boolean add(ReservationApModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservationApDaoImpl.SQL_ADD_RESERVATION);
            pst.setInt(1, value.getCustomer().getIdCustomer());
            pst.setInt(2, value.getUser().getIdUser());
            pst.setInt(3, value.getTypeReservation().getIdTypeReservation());
            pst.setInt(4, value.getReservedDays().getIdReservedDay());
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
    public boolean update(ReservationApModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservationApDaoImpl.SQL_UPDATE_RESERVATION);
            pst.setInt(1, value.getCustomer().getIdCustomer());
            pst.setInt(2, value.getUser().getIdUser());
            pst.setInt(3, value.getTypeReservation().getIdTypeReservation());
            pst.setInt(4, value.getReservedDays().getIdReservedDay());
            pst.setInt(5, value.getIdReservationQuote());
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
    public boolean delete(ReservationApModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservationApDaoImpl.SQL_DELETE_RESERVATION);
            pst.setInt(1, value.getIdReservationQuote());
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
    public ObservableList<ReservationTable> loadTable() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<ReservationTable> reservationList = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservationApDaoImpl.SQL_LOAD_RESERVATION_AP);
            rs = pst.executeQuery();

            reservationList.clear();

            returnResults(rs, reservationList);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return reservationList;
    }

    @Override
    public ObservableList<ReservationTable> findReservation(ReservationTable customerTable) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<ReservationTable> reservationList = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservationApDaoImpl.SQL_FIND_RESERVATION_AP);
            pst.setString(1, customerTable.getCustomerDni());
            rs = pst.executeQuery();

            reservationList.clear();

            returnResults(rs, reservationList);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return reservationList;
    }

    private void returnResults(ResultSet rs, ObservableList<ReservationTable> reservationList) throws SQLException {
        while (rs.next()) {
            var customerTable = new ReservationTable(
                    rs.getInt("id_reservation_quote"),
                    rs.getInt("id_customer"),
                    rs.getInt("id_user"),
                    rs.getInt("id_type_reservations"),
                    rs.getInt("id_reserved_day"),
                    rs.getString("customer_name"),
                    rs.getString("customer_last_name"),
                    rs.getString("customer_dni"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("reservation_date"),
                    rs.getString("user_name"),
                    rs.getString("user_dni")
            );

            reservationList.add(customerTable);
        }
    }
}

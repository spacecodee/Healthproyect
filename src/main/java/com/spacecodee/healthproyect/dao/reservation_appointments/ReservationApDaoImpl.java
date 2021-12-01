package com.spacecodee.healthproyect.dao.reservation_appointments;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dto.customers.CustomerDto;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.dto.reservation_appointments.ReservationApDto;
import com.spacecodee.healthproyect.dto.reserved_days.ReservedDaysDto;
import com.spacecodee.healthproyect.dto.type_reservations.TypeReservationDto;
import com.spacecodee.healthproyect.dto.users.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationApDaoImpl implements IReservationApDao {

    private static final String SQL_LOAD_RESERVATION_AP = "SELECT r.id_reservation_quote," +
            "       p.id_people," +
            "       r.id_customer," +
            "       r.id_user," +
            "       r.id_type_reservations," +
            "       r.id_reserved_day," +
            "       p.name      AS customer_name," +
            "       p.last_name AS customer_last_name," +
            "       p.dni       AS customer_dni," +
            "       tr.name," +
            "       tr.price," +
            "       rd.reservation_date," +
            "       u.user_name," +
            "       pu.dni      AS user_dni " +
            "FROM reservation_appointments r" +
            "         INNER JOIN customers c on r.id_customer = c.id_customer" +
            "         INNER JOIN users u on r.id_user = u.id_user" +
            "         INNER JOIN peoples p on c.id_people = p.id_people" +
            "         INNER JOIN peoples pu on u.id_people = pu.id_people" +
            "         INNER JOIN type_reservations tr on r.id_type_reservations = tr.id_type_reservation" +
            "         INNER JOIN reserved_days rd on r.id_reserved_day = rd.id_reserved_day";
    private static final String SQL_FIND_RESERVATION_AP = "SELECT r.id_reservation_quote," +
            "       p.id_people," +
            "       r.id_customer," +
            "       r.id_user," +
            "       r.id_type_reservations," +
            "       r.id_reserved_day," +
            "       p.name      AS customer_name," +
            "       p.last_name AS customer_last_name," +
            "       p.dni       AS customer_dni," +
            "       tr.name," +
            "       tr.price," +
            "       rd.reservation_date," +
            "       u.user_name," +
            "       pu.dni      AS user_dni " +
            "FROM reservation_appointments r" +
            "         INNER JOIN customers c on r.id_customer = c.id_customer" +
            "         INNER JOIN users u on r.id_user = u.id_user" +
            "         INNER JOIN peoples p on c.id_people = p.id_people" +
            "         INNER JOIN peoples pu on u.id_people = pu.id_people" +
            "         INNER JOIN type_reservations tr on r.id_type_reservations = tr.id_type_reservation" +
            "         INNER JOIN reserved_days rd on r.id_reserved_day = rd.id_reserved_day " +
            "WHERE p.dni LIKE CONCAT('%', ?, '%')";
    private static final String SQL_ADD_RESERVATION = "INSERT INTO reservation_appointments (id_customer, id_user," +
            "id_type_reservations, id_reserved_day) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_RESERVATION = "UPDATE reservation_appointments " +
            "SET id_customer          = ?," +
            "    id_user              = ?," +
            "    id_type_reservations = ?," +
            "    id_reserved_day      = ? " +
            "WHERE id_reservation_quote = ?";
    private static final String SQL_DELETE_RESERVATION = "DELETE FROM reservation_appointments " +
            "WHERE id_reservation_quote = ?";

    @Override
    public ArrayList<ReservationApDto> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ReservationApDto> listReservationAp = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservationApDaoImpl.SQL_LOAD_RESERVATION_AP);
            rs = pst.executeQuery();

            returnResults(rs, listReservationAp);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listReservationAp;
    }

    public ArrayList<ReservationApDto> findValue(ReservationApDto value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ReservationApDto> listReservationAp = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(ReservationApDaoImpl.SQL_FIND_RESERVATION_AP);
            pst.setString(1, value.getCustomer().getPeople().getDni());
            rs = pst.executeQuery();

            returnResults(rs, listReservationAp);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listReservationAp;
    }

    @Override
    public boolean add(ReservationApDto value) {
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
    public boolean update(ReservationApDto value) {
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
    public boolean delete(ReservationApDto value) {
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

    private void returnResults(ResultSet rs, ArrayList<ReservationApDto> reservationList) throws SQLException {
        while (rs.next()) {
            var reservations = new ReservationApDto(
                    rs.getInt("id_reservation_quote"),
                    new CustomerDto(
                            rs.getInt("id_customer"),
                            new PeopleDto(
                                    rs.getInt("id_customer"), rs.getString("customer_dni"),
                                    rs.getString("customer_name"), rs.getString("customer_last_name")
                            )
                    ),
                    new UserDto(
                            rs.getInt("id_user"), rs.getString("user_name"),
                            new PeopleDto(rs.getString("user_dni"))
                    ),
                    new TypeReservationDto(
                            rs.getInt("id_type_reservations"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    ),
                    new ReservedDaysDto(
                            rs.getInt("id_reserved_day"),
                            rs.getString("reservation_date")
                    )
            );

            reservationList.add(reservations);
        }
    }
}

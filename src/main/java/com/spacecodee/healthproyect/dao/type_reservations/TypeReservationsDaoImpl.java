package com.spacecodee.healthproyect.dao.type_reservations;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.type_reservations.TypeReservationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    @Override
    public ObservableList<TypeReservationModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<TypeReservationModel> typeReservationList = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_LOAD_TYPE_RESERVATION_AP);
            rs = pst.executeQuery();

            typeReservationList.clear();

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
    public ObservableList<TypeReservationModel> findByName(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<TypeReservationModel> typeReservationList = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_FIND_TYPE_RESERVATION_AP);
            pst.setString(1, name);
            rs = pst.executeQuery();

            typeReservationList.clear();

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
    public boolean add(TypeReservationModel value) {
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
    public boolean update(TypeReservationModel value) {
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
    public boolean delete(TypeReservationModel value) {
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

    @Override
    public ArrayList<TypeReservationModel> listOfTypeReservations() {
        ArrayList<TypeReservationModel> listTypeReservations = new ArrayList<>();
        TypeReservationModel typeReservationModel;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(TypeReservationsDaoImpl.SQL_LOAD_TYPE_RESERVATION_AP);
            rs = pst.executeQuery();

            while (rs.next()) {
                typeReservationModel = new TypeReservationModel(
                        rs.getInt("id_type_reservation"),
                        rs.getString("name"),
                        rs.getDouble("price")
                );

                listTypeReservations.add(typeReservationModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return listTypeReservations;
    }

    private void returnResults(ResultSet rs, ObservableList<TypeReservationModel> typeReservationList) throws SQLException {
        while (rs.next()) {
            var typeReservationModel = new TypeReservationModel(
                    rs.getInt("id_type_reservation"),
                    rs.getString("name"),
                    rs.getDouble("price")
            );

            typeReservationList.add(typeReservationModel);
        }
    }
}

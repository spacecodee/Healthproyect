package com.spacecodee.healthproyect.dao.customers;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.customers.CustomerModel;
import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements ICustomerDao {

    private static final String SQL_LOAD_CUSTOMERS = "SELECT p.name,\n" +
            "       p.last_name,\n" +
            "       c.user_name,\n" +
            "       p.dni,\n" +
            "       p.mail,\n" +
            "       p.phone\n" +
            "FROM customers c\n" +
            "         INNER JOIN peoples p\n" +
            "                    on c.id_people = p.id_people";
    private static final String SQL_ADD_CUSTOMER = "INSERT INTO customers (user_name, id_people)\n" +
            "VALUES (?, ?);";
    private static final String SQL_UPDATE_CUSTOMER = "UPDATE customers\n" +
            "SET user_name = ?,\n" +
            "    id_people = ?\n" +
            "WHERE id_customer = ?;";
    private static final String SQL_DELETE_CUSTOMER = "DELETE\n" +
            "FROM customers\n" +
            "WHERE id_customer = ?;";
    private static final String SQL_FIND_CUSTOMER_BY_USERNAME = "SELECT p.name,\n" +
            "       p.last_name,\n" +
            "       c.user_name,\n" +
            "       p.dni,\n" +
            "       p.mail,\n" +
            "       p.phone\n" +
            "FROM customers c\n" +
            "         INNER JOIN peoples p\n" +
            "                    on c.id_people = p.id_people\n" +
            "WHERE c.user_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%');";

    @Override
    public ObservableList<CustomerModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CustomerModel> customerList = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_LOAD_CUSTOMERS);
            rs = pst.executeQuery();

            customerList.clear();

            returnResults(rs, customerList);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return customerList;
    }

    @Override
    public ObservableList<CustomerModel> findByName(String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<CustomerModel> customers = FXCollections.observableArrayList();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_FIND_CUSTOMER_BY_USERNAME);
            pst.setString(1, name);
            rs = pst.executeQuery();

            customers.clear();

            returnResults(rs, customers);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return customers;
    }

    private void returnResults(ResultSet rs, ObservableList<CustomerModel> customers) throws SQLException {
        while (rs.next()) {
            var people = new PeopleModel(
                    rs.getString("dni"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("mail"),
                    rs.getString("phone")
            );

            var customerModel = new CustomerModel(
                    rs.getString("user_name"),
                    people
            );

            customers.add(customerModel);
        }
    }

    @Override
    public boolean add(CustomerModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_ADD_CUSTOMER);
            pst.setString(1, value.getUserName());
            pst.setInt(2, value.getIdCustomer());
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
    public boolean update(CustomerModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_UPDATE_CUSTOMER);
            pst.setString(1, value.getUserName());
            pst.setInt(2, value.getPeople().getIdPeople());
            pst.setInt(3, value.getIdCustomer());
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
    public boolean delete(CustomerModel value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_DELETE_CUSTOMER);
            pst.setInt(1, value.getIdCustomer());
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

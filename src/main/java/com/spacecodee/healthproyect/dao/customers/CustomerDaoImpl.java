package com.spacecodee.healthproyect.dao.customers;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.model.customers.CustomerModel;
import com.spacecodee.healthproyect.model.peoples.PeopleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements ICustomerDao {

    private static final String SQL_LOAD_CUSTOMERS = "SELECT c.id_customer, " +
            "       c.id_people, " +
            "       c.user_name, " +
            "       p.name, " +
            "       p.last_name, " +
            "       p.mail, " +
            "       p.dni, " +
            "       p.phone, " +
            "       c.user_name " +
            "FROM customers c " +
            "         INNER JOIN peoples p on c.id_people = p.id_people";
    private static final String SQL_ADD_CUSTOMER = "INSERT INTO customers (user_name, id_people) " +
            "VALUES (?, ?)";
    private static final String SQL_UPDATE_CUSTOMER = "UPDATE customers " +
            "SET user_name = ?, " +
            "    id_people = ? " +
            "WHERE id_customer = ?";
    private static final String SQL_DELETE_CUSTOMER = "DELETE " +
            "FROM customers " +
            "WHERE id_customer = ?";
    private static final String SQL_FIND_CUSTOMER = "SELECT c.id_customer, " +
            "       c.id_people, " +
            "       c.user_name, " +
            "       p.name, " +
            "       p.last_name, " +
            "       p.mail, " +
            "       p.dni, " +
            "       p.phone, " +
            "       c.user_name " +
            "FROM customers c " +
            "         INNER JOIN peoples p on c.id_people = p.id_people " +
            "WHERE p.dni LIKE CONCAT('%', ?, '%') " +
            "   AND c.user_name COLLATE UTF8_GENERAL_CI LIKE CONCAT('%', ?, '%')";

    @Override
    public ArrayList<CustomerModel> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerModel> customerList = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_LOAD_CUSTOMERS);
            rs = pst.executeQuery();

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
    public ArrayList<CustomerModel> findValue(CustomerModel value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerModel> customerList = new ArrayList<>();

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_FIND_CUSTOMER);
            pst.setString(1, value.getPeople().getDni());
            pst.setString(2, value.getUserName());
            rs = pst.executeQuery();

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

    private void returnResults(ResultSet rs, ArrayList<CustomerModel> customers) throws SQLException {
        while (rs.next()) {
            var customer = new CustomerModel(
                    rs.getInt("id_customer"), rs.getString("user_name"),
                    new PeopleModel(
                            rs.getInt("id_people"), rs.getString("dni"),
                            rs.getString("name"), rs.getString("last_name"),
                            rs.getString("mail"), rs.getString("phone")
                    )
            );

            customers.add(customer);
        }
    }
}

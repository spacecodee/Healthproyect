package com.spacecodee.healthproyect.dao.customers;

import com.spacecodee.healthproyect.dao.Connexion;
import com.spacecodee.healthproyect.dao.users.UserDaoImpl;
import com.spacecodee.healthproyect.dto.customers.CustomerDto;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;

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
    private static final String SQL_FIND_CUSTOMER_BY_DNI = "SELECT c.id_customer, " +
            "       c.id_people, " +
            "       c.user_name, " +
            "       p.name, " +
            "       p.last_name, " +
            "       p.mail, " +
            "       p.dni, " +
            "       p.phone, " +
            "       p.birth_date, " +
            "       c.user_name " +
            "FROM customers c " +
            "         INNER JOIN peoples p on c.id_people = p.id_people " +
            "WHERE p.dni = ?";
    private static final String SQL_MAX_CUSTOMER_ID = "SELECT MAX(id_customer) AS id FROM customers";
    private static final String SQL_COUNT_CUSTOMERS = "SELECT COUNT(id_customer) AS total FROM  customers";
    private static final String SQL_VALIDATE_REPEAT_USERNAME = "SELECT COUNT(id_user) AS total FROM customers WHERE user_name = ?";

    @Override
    public ArrayList<CustomerDto> load() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerDto> customerList = new ArrayList<>();

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
    public ArrayList<CustomerDto> findValue(CustomerDto value) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerDto> customerList = new ArrayList<>();

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
    public boolean add(CustomerDto value) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_ADD_CUSTOMER);
            pst.setString(1, value.getUserName());
            pst.setInt(2, value.getPeople().getIdPeople());
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
    public boolean update(CustomerDto value) {
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
    public boolean delete(CustomerDto value) {
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

    private void returnResults(ResultSet rs, ArrayList<CustomerDto> customers) throws SQLException {
        while (rs.next()) {
            var customer = new CustomerDto(
                    rs.getInt("id_customer"), rs.getString("user_name"),
                    new PeopleDto(
                            rs.getInt("id_people"), rs.getString("dni"),
                            rs.getString("name"), rs.getString("last_name"),
                            rs.getString("mail"), rs.getString("phone")
                    )
            );

            customers.add(customer);
        }
    }

    @Override
    public CustomerDto findCustomerByDni(String dni) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        CustomerDto peopleDto = null;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_FIND_CUSTOMER_BY_DNI);
            pst.setString(1, dni);
            rs = pst.executeQuery();

            while (rs.next()) {
                peopleDto = new CustomerDto(
                        rs.getInt("id_customer"), rs.getString("user_name"),
                        new PeopleDto(
                                rs.getInt("id_people"), rs.getString("dni"),
                                rs.getString("name"), rs.getString("last_name"),
                                rs.getString("mail"), rs.getString("phone"),
                                rs.getString("birth_date")
                        )
                );
            }

            return peopleDto;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            assert rs != null;
            Connexion.close(rs);
            Connexion.close(pst);
            Connexion.close(conn);
        }

        return peopleDto;
    }

    @Override
    public int validateRepeatUsername(String username) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var total = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_VALIDATE_REPEAT_USERNAME);
            pst.setString(1, username);
            rs = pst.executeQuery();

            if (rs.next()) {
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
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_MAX_CUSTOMER_ID);
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

    @Override
    public int total() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        var total = 0;

        try {
            conn = Connexion.getConnection();
            pst = conn.prepareStatement(CustomerDaoImpl.SQL_COUNT_CUSTOMERS);
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
}

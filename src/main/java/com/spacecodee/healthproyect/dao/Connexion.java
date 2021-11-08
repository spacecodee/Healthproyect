package com.spacecodee.healthproyect.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connexion {

    private static final String JDBC_BD = "health_dashboard";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "spacecodee";
    private static final String JDBC_REQUIREMENTS = "?useSSL=false&useTimezone=true&serverTimezone=UTC" +
            "&allowPublicKeyRetrieval=true";
    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/" + Connexion.JDBC_BD + Connexion.JDBC_REQUIREMENTS;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static BasicDataSource dataSource;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(Connexion.JDBC_DRIVER);
            dataSource.setUrl(Connexion.JDBC_URL);
            dataSource.setUsername(Connexion.JDBC_USER);
            dataSource.setPassword(Connexion.JDBC_PASSWORD);
            dataSource.setInitialSize(50);
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return Connexion.getDataSource().getConnection();
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement ps) {
        try {
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}

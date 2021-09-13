package com.spacecodee.healthproyect.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    private static final String JDBC_BD = "health_dashboard";

    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "spacecodee";
    private static final String JDBC_REQUERIMIENTOS = "?useSSL=false&useTimezone=true&serverTimezone=UTC" +
            "&allowPublicKeyRetrieval=true";
    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/" + Conexion.JDBC_BD + Conexion.JDBC_REQUERIMIENTOS;
    private static BasicDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(Conexion.JDBC_URL);
            dataSource.setUsername(Conexion.JDBC_USER);
            dataSource.setPassword(Conexion.JDBC_PASSWORD);
            dataSource.setInitialSize(50);
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return Conexion.getDataSource().getConnection();
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

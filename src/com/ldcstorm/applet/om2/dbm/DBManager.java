package com.ldcstorm.applet.om2.dbm;

import com.ldcstorm.applet.om2.dto.DTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBManager {
    protected static final String DBURL = "jdbc:mysql://localhost:9633/30561dbCsjza";
    protected static final String USERNAME = "30561_fH8714";
    protected static final String PASSWORD = "kDTHq98xE3bcjkZ";

    private DBManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final DBManager dbManager = new DBManager();

    public static DBManager getDbManager() {
        return dbManager;
    }

    /**
     * Getting object with single column and value
     * @param dtm Build a object and then cast to O
     * @param column A column name from your database
     * @param value A data value from that column
     * @param <O> As you can see, O extend from DTO
     * @return
     */
    public <O extends DTO> O getObj(DTM<O> dtm, String column, String value) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            try (Statement statement = conn.createStatement()) {
                return dtm.get(statement, column, value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <O extends DTO> List<O> getAll(DTM<O> dtm) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            try (Statement statement = conn.createStatement()) {
                return dtm.getAll(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <O extends DTO> boolean addObj(DTM<O> dtm, O o) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            try (Statement statement = conn.createStatement()) {
                return dtm.add(statement, o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <O extends DTO> boolean update(DTM<O> dtm, O o) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            try (Statement statement = conn.createStatement()) {
                return dtm.update(statement, o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <O extends DTO> boolean delete(DTM<O> dtm, O o) {
        try (Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD)) {
            try (Statement statement = conn.createStatement()) {
                return dtm.delete(statement, o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}


package com.ldcstorm.applet.om2.dbm;

import com.ldcstorm.applet.om2.dto.DTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBManager {
    /**
     * Database parameters for initialize your database
     */
    protected static final String DBURL = "jdbc:mysql://localhost:9633/30561dbCsjza";
    protected static final String USERNAME = "30561_fH8714";
    protected static final String PASSWORD = "kDTHq98xE3bcjkZ";

    /**
     * As you can see, dbManager is a singleton object
     */
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
     *
     * @param dtm    A tool for building object type of O
     * @param column A column name from your database
     * @param value  A data value from that column
     * @param <O>    As you can see, O extend from DTO
     * @return Object cast to O and then return that
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

    /**
     * Getting All object
     *
     * @param dtm A tool for building object type of O
     * @param <O> As you can see, O extend from DTO
     * @return All Object will add to List<O>, but no filter
     */
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

    /**
     * Insert o under beneath the last row with dtm from database
     *
     * @param dtm A tool for building object type of O
     * @param o   just a object you wanted to do something
     * @param <O> As you can see, O extend from DTO
     * @return return the action whether success
     */
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

    /**
     * update o with dtm from database
     *
     * @param dtm A tool for building object type of O
     * @param o   just a object you wanted to do something
     * @param <O> As you can see, O extend from DTO
     * @return return the action whether success
     */
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

    /**
     * delete o(a whole row) with dtm from database
     *
     * @param dtm A tool for building object type of O
     * @param o   just a object you wanted to do something
     * @param <O> As you can see, O extend from DTO
     * @return return the action whether success
     */
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


package org.ldccc.om2.dbm;

import org.ldccc.om2.dto.DTO;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DTM<O extends DTO> {
    private Class<O> tClass;
    protected String base;

    /**
     * Constructor of DTM of the only
     *
     * @param tClass We will construct the objects of new with tClass continuously
     * @param base   A database of you wanted to connected
     */
    protected DTM(Class<O> tClass, String base) {
        this.tClass = tClass;
        if (base != null) {
            this.base = base;
        } else {
            throw new InvalidParameterException("Database can't be NULL");
        }
    }

    /**
     * Getting a object of new with tClass and map
     *
     * @param map That map describing all the data of obj
     * @return As a result of newInstance(map), we final got a object of complete, and return it
     */
    private O getO(Map<String, Object> map) {
        try {
            return tClass.getConstructor(Map.class).newInstance(map);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * In accordance with the different class name cast the return value to an appropriate of type of encapsulation for map
     *
     * @param set  A resultset from your SQL statement you are executing
     * @param data A metadata to describing info of your resultset
     * @return That map is the core of object of building, final we building a complete object with that map after that
     * @throws SQLException When we used an inside object from package of java.sql, we must be throws/catch that Exception
     */
    private Map<String, Object> getMap(ResultSet set, ResultSetMetaData data) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        for (int i = 1; i <= data.getColumnCount(); i++) {
            String clsName = data.getColumnClassName(i);
            if (clsName.equals(String.class.getName())) {
                map.put(data.getColumnLabel(i), set.getString(i));
            } else if (clsName.equals(Integer.class.getName())) {
                map.put(data.getColumnLabel(i), set.getInt(i));
            } else if (clsName.equals(Date.class.getName())) {
                map.put(data.getColumnLabel(i), set.getDate(i));
            } else if (clsName.equals(Boolean.class.getName())) {
                map.put(data.getColumnLabel(i), set.getBoolean(i));
            } else {
                map.put(data.getColumnLabel(i), set.getObject(i));
            }
        }
        return map;
    }

    /**
     * Packaging a simply SQL statement for get a single object
     *
     * @param statement A statement from DBManager
     * @param column    A column name from your database
     * @param value     A data value from that column
     * @return Return overloading method of get(statement, sql)
     */
    protected O get(Statement statement, String column, String value) {
        String sql = "SELECT * FROM " + base + " WHERE " + column + "='" + value + "';";
        return get(statement, sql);
    }

    /**
     * Packaging a simply SQL statement for get all object
     *
     * @param statement A statement from DBManager
     * @return Return overloading method of getAll(statement, sql)
     */
    protected List<O> getAll(Statement statement) {
        String sql = "SELECT * FROM " + base + ";";
        return getAll(statement, sql);
    }

    /**
     * Packaging a simply SQL statement for insert object
     *
     * @param statement A statement from DBManager
     * @param o         Just a object you wanted to insert
     * @return Return overloading method of add(statement, sql)
     */
    protected boolean add(Statement statement, O o) {
        String sql = "INSERT INTO " + base + o.toInsertSQL();
        return add(statement, sql);
    }

    /**
     * Packaging a simply SQL statement for update object
     *
     * @param statement A statement from DBManager
     * @param o         Just a object you wanted to insert
     * @return Return overloading method of update(statement, sql)
     */
    protected boolean update(Statement statement, O o) {
        String sql = "UPDATE " + base + o.toUpdateSQL();
        return update(statement, sql);
    }

    /**
     * Packaging a simply SQL statement for delete object
     *
     * @param statement A statement from DBManager
     * @param o         Just a object you wanted to insert
     * @return Return overloading method of delete(statement, sql)
     */
    protected boolean delete(Statement statement, O o) {
        String sql = "DELETE FROM " + base + o.toDeleteSQL();
        return delete(statement, sql);
    }

    /**
     * Getting a single object
     *
     * @param statement A statement from DBManager
     * @param sql       The DriverManager will keep this statement of sql and executing
     * @return Utilize tClass and map we final got a complete object, so return that
     */
    protected O get(Statement statement, String sql) {
        try (ResultSet set = statement.executeQuery(sql)) {
            ResultSetMetaData data = set.getMetaData();
            set.first();
            Map<String, Object> map = getMap(set, data);
            return getO(map);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Getting all object
     *
     * @param statement A statement from DBManager
     * @param sql       The DriverManager will keep this statement of sql and executing
     * @return We got a complete object, next we that put into a List, final return the list
     */
    protected List<O> getAll(Statement statement, String sql) {
        try (ResultSet set = statement.executeQuery(sql)) {
            ResultSetMetaData data = set.getMetaData();
            List<O> objs = new ArrayList<>();
            while (set.next()) {
                Map<String, Object> map = getMap(set, data);
                O o = getO(map);
                objs.add(o);
            }
            return objs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inserting object beneath the last row
     *
     * @param statement A statement from DBManager
     * @param sql       The DriverManager will keep this statement of sql and executing
     * @return Return whether insertion is successful
     */
    protected boolean add(Statement statement, String sql) {
        try {
            return statement.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updating object
     *
     * @param statement A statement from DBManager
     * @param sql       The DriverManager will keep this statement of sql and executing
     * @return Return whether updation is successful
     */
    protected boolean update(Statement statement, String sql) {
        try {
            return statement.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deleting object
     *
     * @param statement A statement from DBManager
     * @param sql       The DriverManager will keep this statement of sql and executing
     * @return Return whether deletion is successful
     */
    protected boolean delete(Statement statement, String sql) {
        try {
            return statement.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

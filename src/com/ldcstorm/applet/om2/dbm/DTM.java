package com.ldcstorm.applet.om2.dbm;

import com.ldcstorm.applet.om2.dto.DTO;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DTM<O extends DTO> {
    protected String base;

    protected O get(Statement statement, String column, String value) {
        String sql = "SELECT * FROM " + base + " WHERE " + column + "='" + value + "';";
        return get(statement, sql);
    }

    protected List<O> getAll(Statement statement) {
        String sql = "SELECT * FROM " + base + ";";
        return getAll(statement, sql);
    }

    protected boolean add(Statement statement, O o) {
        String sql = "INSERT INTO " + base + o.toInsertSQL();
        return add(statement, sql);
    }

    protected boolean update(Statement statement, O o) {
        String sql = "UPDATE " + base + o.toUpdateSQL();
        return update(statement, sql);
    }

    protected boolean delete(Statement statement, O o) {
        String sql = "DELETE FROM " + base + o.toDeleteSQL();
        return delete(statement, sql);
    }


    private Class<O> tClass;

    protected DTM(Class<O> tClass, String base) {
        this.tClass = tClass;
        if (base != null) {
            this.base = base;
        } else {
            throw new InvalidParameterException("Database can't be NULL");
        }
    }

    private O getO(Map<String, Object> map) {
        try {
            return tClass.getConstructor(Map.class).newInstance(map);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    protected boolean add(Statement statement, String sql) {
        try {
            return statement.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean update(Statement statement, String sql) {
        try {
            return statement.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean delete(Statement statement, String sql) {
        try {
            return statement.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

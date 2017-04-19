package com.ldcstorm.applet.om2.dto;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DTO {
    public static final String ID = "ID";
    public Map<String, Object> map = new HashMap<>();

    public DTO() {
    }

    public DTO(Map<String, Object> map) {
        this.map = map;
    }

    public <O> void setObj(String column, O value) {
        map.put(column, value);
    }

    public void setString(String column, String value) {
        map.put(column, value);
    }

    public void setInt(String column, Integer value) {
        map.put(column, value);
    }

    public void setDate(String column, Date value) {
        map.put(column, value);
    }

    public void setBool(String column, Boolean value) {
        map.put(column, value);
    }

    public <O> O getObj(String column) {
        return (O) map.get(column);
    }

    public String getString(String column) {
        return (String) map.get(column);
    }

    public Integer getInt(String column) {
        return (Integer) map.get(column);
    }

    public Date getDate(String column) {
        return (Date) map.get(column);
    }

    public Boolean getBool(String column) {
        return (Boolean) map.get(column);
    }

    public String sqlStyle(String column) {
        Object o = map.get(column);
        if (o instanceof Boolean || o instanceof Integer || o instanceof Long) {
            return o.toString();
        } else {
            return "\'" + map.get(column) + "\'";
        }
    }

    public String toInsertSQL() {
        return "(" + String.join(",", map.keySet()) + ")" +
                "VALUES" +
                "(" + map.keySet().stream().map(this::sqlStyle).collect(Collectors.joining(",")) + ");";
    }

    public String toUpdateSQL() {
        return " SET " +
                map.keySet().stream().map(s -> s + "=" + this.sqlStyle(s)).collect(Collectors.joining(",")) +
                " WHERE " + ID + "=" + this.getObj(ID) + ";";
    }

    public String toDeleteSQL() {
        return " WHERE " + ID + "=" + this.getObj(ID) + ";";
    }

    @Override
    public int hashCode() {
        int keyCode = map.keySet().stream().map(String::hashCode).reduce(Integer::compareTo).orElse(0);
        int valueCode = map.values().stream().map(Object::hashCode).reduce(Integer::compareTo).orElse(0);
        return (keyCode + valueCode) * 31;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass()) && this.hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}

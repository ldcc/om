package com.ldcstorm.applet.om2.dto;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DTO {
    public static final String ID = "ID";
    public Map<String, Object> map = new HashMap<>();

    public DTO(Map<String, Object> map) {
        this.map = map;
    }

    public DTO(Object[]... args) {
        map.put(ID, 0);
    }

    /**
     * Setting one of the date value
     *
     * @param column A column name from your database
     * @param value  A data value from that column
     * @param <O>    Source type of the value, it's been cast up before,just cast down again
     */
    public <O> void setObj(String column, O value) {
        map.put(column, value);
    }

    @Deprecated
    public void setString(String column, String value) {
        map.put(column, value);
    }

    @Deprecated
    public void setInt(String column, Integer value) {
        map.put(column, value);
    }

    @Deprecated
    public void setDate(String column, Date value) {
        map.put(column, value);
    }

    @Deprecated
    public void setBool(String column, Boolean value) {
        map.put(column, value);
    }

    /**
     * Getting one of the data value
     *
     * @param column A column name from your database
     * @param <O>    Source type of the value, it's been cast up before,just cast down again
     * @return Getting a data value from that column, return it
     */
    public <O> O getObj(String column) {
        return (O) map.get(column);
    }

    @Deprecated
    public String getString(String column) {
        return (String) map.get(column);
    }

    @Deprecated
    public Integer getInt(String column) {
        return (Integer) map.get(column);
    }

    @Deprecated
    public Date getDate(String column) {
        return (Date) map.get(column);
    }

    @Deprecated
    public Boolean getBool(String column) {
        return (Boolean) map.get(column);
    }

    /**
     * Packaging the data value(value -> 'value')
     *
     * @param column A column name from your database
     * @return The package value
     */
    public String sqlStyle(String column) {
        Object o = map.get(column);
        if (o instanceof Boolean || o instanceof Integer || o instanceof Long) {
            return o.toString();
        } else {
            return "\'" + map.get(column) + "\'";
        }
    }

    /**
     * Parse object to a insert statement of SQL
     *
     * @return Return parsed
     */
    public String toInsertSQL() {
        return "(" + String.join(",", map.keySet()) + ")" +
                "VALUES" +
                "(" + map.keySet().stream().map(this::sqlStyle).collect(Collectors.joining(",")) + ");";
    }

    /**
     * Parse object to a update statement of SQL
     *
     * @return Return parsed
     */
    public String toUpdateSQL() {
        return " SET " +
                map.keySet().stream().map(s -> s + "=" + this.sqlStyle(s)).collect(Collectors.joining(",")) +
                " WHERE " + ID + "=" + this.getObj(ID) + ";";
    }

    /**
     * Parse object to a delete statement of SQL
     *
     * @return Return parsed
     */
    public String toDeleteSQL() {
        return " WHERE " + ID +
                "=" +
                this.getObj(ID) + ";";
    }

    @Override
    public String toString() {
        return map.toString();
    }

    /**
     * In accordance with the map getting hashcode of keys and values respective
     *
     * @return (keyCode add valueCode) * 31 is our hashcode
     */
    @Override
    public int hashCode() {
        int keyCode = map.keySet().stream().map(String::hashCode).reduce(Integer::compareTo).orElse(0);
        int valueCode = map.values().stream().map(Object::hashCode).reduce(Integer::compareTo).orElse(0);
        return (keyCode + valueCode) * 31;
    }

    /**
     * Judge objs whether equal, in accordance with the hashcode not by self
     *
     * @param obj Another object
     * @return They hashcode is equals
     */
    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass()) && this.hashCode() == obj.hashCode();
    }
}

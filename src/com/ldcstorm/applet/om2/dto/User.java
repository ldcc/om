package com.ldcstorm.applet.om2.dto;

import java.util.Date;
import java.util.Map;

public class User extends DTO {
    public static final String USER_BASE = ".users";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PASS = "USER_PASSWORD";
    public static final String USER_AUTH = "AUTH";
    public static final String USER_CREATE_TIME = "CREATE_TIME";

    public User() {
        super();
    }

    public User(Map<String, Object> map) {
        this.map = map;
    }

    public User(Integer id, String name, String password, Boolean auth, Date createTime) {
        map.put(ID, id);
        map.put(USER_NAME, name);
        map.put(USER_PASS, password);
        map.put(USER_AUTH, auth);
        map.put(USER_CREATE_TIME, createTime);
    }
}
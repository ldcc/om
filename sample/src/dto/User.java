package dto;

import org.ldccc.om2.dbm.DTM;
import org.ldccc.om2.dto.DTO;

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

    public User(String name, String password, Boolean auth, Date createTime) {
        super();
        map.put(USER_NAME, name);
        map.put(USER_PASS, password);
        map.put(USER_AUTH, auth);
        map.put(USER_CREATE_TIME, createTime);
    }

    private static final DTM<User> dtm = new DTUser<>(User.class, User.USER_BASE);

    public static DTM<User> getDtm() {
        return dtm;
    }

    private static class DTUser<O extends DTO> extends DTM<O> {
        private DTUser(Class<O> tClass, String base) {
            super(tClass, base);
        }
    }
}
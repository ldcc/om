package com.ldcstorm.applet.om2.dbm;

import com.ldcstorm.applet.om2.dto.DTO;
import com.ldcstorm.applet.om2.dto.User;

public class DTUser<O extends DTO> extends DTM<O> {
    private DTUser(Class<O> tClass, String base) {
        super(tClass, base);
    }

    private static final DTM<User> dtm = new DTUser<>(User.class, User.USER_BASE);

    public static DTM<User> getDtm() {
        return dtm;
    }
}











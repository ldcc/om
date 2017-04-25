package com.ldcstorm.applet.om2.dbm;

import com.ldcstorm.applet.om2.dto.DTO;
import com.ldcstorm.applet.om2.dto.Notice;

import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class DTNotice<O extends DTO> extends DTM<O> {
    private static final DTM<Notice> dtm = new DTNotice<>(Notice.class, Notice.NOTICE_BASE);

    public static DTM<Notice> getDtm() {
        return dtm;
    }

    private DTNotice(Class<O> tClass, String base) {
        super(tClass, base);
    }

    @Override
    protected List<O> getAll(Statement statement) {
        List<O> os = super.getAll(statement);
        Collections.reverse(os);
        return os;
    }
}

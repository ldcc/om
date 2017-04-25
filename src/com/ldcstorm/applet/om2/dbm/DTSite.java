package com.ldcstorm.applet.om2.dbm;

import com.ldcstorm.applet.om2.dto.DTO;
import com.ldcstorm.applet.om2.dto.Site;

public class DTSite<O extends DTO> extends DTM<O> {
    private static final DTM<Site> dtm = new DTSite<>(Site.class, Site.SITE_BASE);

    public static DTM<Site> getDtm() {
        return dtm;
    }

    private DTSite(Class<O> tClass, String base) {
        super(tClass, base);
    }

}

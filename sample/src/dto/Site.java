package dto;

import org.ldccc.om2.dbm.DTM;
import org.ldccc.om2.dto.DTO;

import java.util.HashMap;
import java.util.Map;

public class Site extends DTO {
    public static final String SITE_BASE = ".sites";
    public static final String SITE_NAME = "SITE_NAME";
    public static final String SITE_URL = "SITE_URL";

    public Site(Map<String, Object> map) {
        super(map);
    }

    public Site(String name, String url) {
        super(toMap(name, url));
    }

    public static Map<String, Object> toMap(String name, String url) {
        Map<String, Object> map = new HashMap<>();
        map.put(SITE_NAME, name);
        map.put(SITE_URL, url);
        return map;
    }

    private static final DTM<Site> dtm = new DTSite<>(Site.class, Site.SITE_BASE);

    public static DTM<Site> getDtm() {
        return dtm;
    }

    private static class DTSite<O extends DTO> extends DTM<O> {
        private DTSite(Class<O> tClass, String base) {
            super(tClass, base);
        }
    }
}

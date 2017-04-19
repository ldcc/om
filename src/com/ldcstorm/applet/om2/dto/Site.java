package com.ldcstorm.applet.om2.dto;

import java.util.Map;

public class Site extends DTO {
    public static final String SITE_BASE = ".sites";
    public static final String SITE_NAME = "SITE_NAME";
    public static final String SITE_URL = "SITE_URL";

    public Site() {
        super();
    }

    public Site(Map<String, Object> map) {
        this.map = map;
    }

    public Site(Integer id, String name, String url) {
        map.put(ID, id);
        map.put(SITE_NAME, name);
        map.put(SITE_URL, url);
    }
}

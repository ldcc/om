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

    public Site(String name, String url) {
        super();
        map.put(SITE_NAME, name);
        map.put(SITE_URL, url);
    }
}

package com.ldcstorm.applet.om2.dto;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Notice extends DTO {
    public static final String NOTICE_BASE = ".notices";
    public static final String UID = "UID";
    public static final String TITLE = "TITLE";
    public static final String CONTENT = "CONTENT";
    public static final String PUB_DATE = "PUB_DATE";
    public static final String PUB_STATUS = "PUB_STATUS";
    public static final String NOTICE_TYPE = "NOTICE_TYPE";
    public static final String IMAGE = "IMAGE";

    public static final List<String> imgList = Arrays.asList("/img/2-1.jpg", "/img/2-2.jpg", "/img/2-3.jpg");

    public Notice() {
        super();
    }

    public Notice(Map<String, Object> map) {
        this.map = map;
    }

    public Notice(Integer uid, String title, String content, Date pubDate, String noticeType) {
        map.put(ID, 0);
        map.put(UID, uid);
        map.put(TITLE, title);
        map.put(CONTENT, content);
        map.put(PUB_DATE, pubDate);
        map.put(PUB_STATUS, true);
        map.put(NOTICE_TYPE, noticeType);
        map.put(IMAGE, imgList.get((int) (Math.random() * 4)));
    }
}

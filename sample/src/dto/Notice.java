package dto;

import org.ldccc.om2.dbm.DTM;
import org.ldccc.om2.dto.DTO;

import java.sql.Date;
import java.sql.Statement;
import java.util.*;

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

    public Notice(Map<String, Object> map) {
        super(map);
    }

    public Notice(Integer uid, String title, String content, Date pubDate, String noticeType) {
        super(toMap(uid, title, content, pubDate, noticeType));
    }

    public static Map<String, Object> toMap(Integer uid, String title, String content, Date pubDate, String noticeType) {
        Map<String, Object> map = new HashMap<>();
        map.put(UID, uid);
        map.put(TITLE, title);
        map.put(CONTENT, content);
        map.put(PUB_DATE, pubDate);
        map.put(PUB_STATUS, true);
        map.put(NOTICE_TYPE, noticeType);
        map.put(IMAGE, imgList.get((int) (Math.random() * 4)));
        return map;
    }

    private static final DTM<Notice> dtm = new DTNotice<>(Notice.class, Notice.NOTICE_BASE);

    public static DTM<Notice> getDtm() {
        return dtm;
    }

    private static class DTNotice<O extends DTO> extends DTM<O> {
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
}


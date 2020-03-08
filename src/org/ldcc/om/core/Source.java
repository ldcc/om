package org.ldcc.om.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class Source {
    private String url;
    private String username;
    private String password;
    private int poolMax;
    private List<Connection> connectionPool;

    private static Source singleton = new Source();

    static Source getSingleton() {
        return singleton;
    }

    private Source() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("jdbc.properties"));
            System.out.println(properties);
        } catch (IOException e) {
            String path = System.getProperty("user.dir");
            System.err.println("cannot find `jdbc.properties` on path: " + path);
            e.printStackTrace();
            System.exit(1);
        }
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(2);
        }
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        poolMax = Integer.parseInt(properties.getProperty("poolmax"));
        connectionPool = new ArrayList<>();
    }

    protected synchronized Connection getConnection() {
        if (connectionPool.size() <= poolMax) {
            try {
                return DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            int lastIndex = connectionPool.size() - 1;
            return connectionPool.remove(lastIndex);
        }
    }

    protected synchronized void closeConnection(Connection conn) {
        if (connectionPool.size() >= poolMax) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            connectionPool.add(conn);
        }
    }
}

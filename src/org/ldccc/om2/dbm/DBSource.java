package org.ldccc.om2.dbm;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBSource {
    /**
     * Connection properties for your Database
     */
    private String url;
    private String username;
    private String password;
    private int poolMax;
    private List<Connection> connectionPool;

    /**
     * DBSource utilizing the "Pattern of Singleton"
     */
    private static DBSource singleton = new DBSource();

    protected static DBSource getSingleton() {
        return singleton;
    }

    /**
     * Load properties that Database who will be using
     */
    private DBSource() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("res/jdbc.properties"));
        } catch (IOException e) {
            System.err.println("找不到 \"jdbc.properties\" 文件，请确定该文件存放在 res 的根目录下");
            e.printStackTrace();
        }
        try {
            Class.forName(properties.getProperty("driver")).newInstance();
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            poolMax = Integer.parseInt(properties.getProperty("poolMax"));
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionPool = new ArrayList<>();
    }

    /**
     * Getting connection utilizing by “multiple connection pool”
     *
     * @return Return one of the connection in the pool
     */
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

    /**
     * Also is a "Connection Pool" Manager
     * If the connection pool are full enough and not any vacant space of having
     * So close a one
     * Else, we put that on the pool for decreasing consuming of computing brought by the Database I/O operate
     *
     * @param conn Just a connection you don't use no longer
     */
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

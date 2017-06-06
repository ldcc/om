package org.ldccc.om3.dbm;

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
			properties.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
			System.out.println(properties);
		} catch (IOException e) {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			System.err.println("找不到 \"jdbc.properties\" 文件，请确定该文件存放在 " + path + " 目录下");
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

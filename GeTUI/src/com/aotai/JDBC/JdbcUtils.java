package com.aotai.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class JdbcUtils {
	
	static PoolProperties p = null;
	static DataSource datasource = null;
	
	public static void jdbcInit() {
		
		if (p != null) {
			return;
		}
		
		p = new PoolProperties();
//		p.setUrl("jdbc:mysql://localhost:3306/solar_info_db");
//		p.setUsername("root");
//		p.setPassword("root");
//		p.setUrl("jdbc:mysql://124.128.61.43:3305/solar_info_db");
		p.setUrl("jdbc:mysql://192.168.0.22:3306/solar_info_db");
		p.setUsername("aotaidianqi");
//		p.setPassword("AoTai81921035_");
		p.setPassword("AoTai81921035_zzy");
		p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(10);
        p.setMaxIdle(3);
        p.setInitialSize(5);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(120);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(2);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
          "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        datasource = new DataSource();
        datasource.setPoolProperties(p);
	}
	
	public static Connection getConnection() {
		
		Connection con = null;
		try {
			con = datasource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con = null;
		}
		
		return con;
		
	}
	
	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test() {
		Connection con = null;
		con = getConnection();
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user");
	        int cnt = 1;
	        while (rs.next()) {
	        	System.out.println((cnt++)+". Host:" +rs.getString("Host")+
	        			" User:"+rs.getString("User")+" Password:"+rs.getString("Password"));
	        }
	        rs.close();
	        st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}
        
	}
	
}

package com.c123.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnectionPool {

    private static final HikariConfig config;
    private static final HikariDataSource ds;

    static {
        config = new HikariConfig();
        config.setJdbcUrl( "jdbc:mysql://127.0.0.1:3306/undertow_example" );
        config.setUsername( "c123_user" );
        config.setPassword( "c123_pass" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private MySQLConnectionPool() {}

}

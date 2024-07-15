package com.GoatHeadMate.Utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

/**
 * Author: GoatHead Mate
 * DATA: 2024/7/15-07-15-下午4:38
 * Description: 工具类，内部含有一个连接池对象，提供连接和回收的方法
 * Version: 1.0
 */
public class JDBCUtils {
    // 连接池对象
    private static DataSource dataSource=null;

    static {
        // 初始化连接池对象
        Properties properties = new Properties();
        InputStream ips = JDBCUtils.class.getClassLoader()
                .getResourceAsStream("druid.properties");
        try {
            properties.load(ips);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对外提供连接的方法
     * @return
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void freeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}

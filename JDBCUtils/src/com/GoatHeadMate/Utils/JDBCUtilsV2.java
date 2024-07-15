package com.GoatHeadMate.Utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Author: GoatHead Mate
 * DATA: 2024/7/15-07-15-下午5:17
 * Description: 利用线程本地变量，存储连接信息！确保一个线程的多个方法可以获得同一个connection
 * 优势:事务操作的时候 service 和 dao 属于同一个线程,不同再传递参数了!
 * 大家都可以调用getConnection自动获取的是相同的连接池!
 * Version: 2.0
 */
public class JDBCUtilsV2 {
    // 连接池对象
    private static DataSource dataSource=null;

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

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
        // 线程本地变量是否存在
        Connection connection = threadLocal.get();
        if (connection == null) {
            // 线程本地变量不存在，获取连接
            connection = dataSource.getConnection();

            // 将连接放入线程本地变量
            threadLocal.set(connection);
        }
        return connection;
    }

    public static void freeConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null) {
            // 移除线程本地变量
            threadLocal.remove();
            // 事务状态回归
            connection.setAutoCommit(true);
            // 关闭连接
            connection.close();
        }
    }
}

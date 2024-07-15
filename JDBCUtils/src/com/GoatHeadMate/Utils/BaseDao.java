package com.GoatHeadMate.Utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: GoatHead Mate
 * DATA: 2024/7/15-07-15-下午5:41
 * Description: 针对数据库的增删改查操作
 * 封装dao层数据库代码，一个简化非dql操作，一个简化dql操作
 * Version: 1.0
 */
public abstract class BaseDao {
    public int executeUpdate(String sql, Object... params) throws SQLException {
        // 获取连接
        JDBCUtilsV2 jdbcUtilsV2 = new JDBCUtilsV2();
        Connection connection = jdbcUtilsV2.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 可变参数params当数组使用
        for (int i = 1; i <= params.length; i++) {
            preparedStatement.setObject(i, params[i-1]);
        }
        int rows = preparedStatement.executeUpdate();
        preparedStatement.close();
        // 是否回收连接需要考虑是不是事务
        if(connection.getAutoCommit()){
            // 如果没有开启事务，就回收
            jdbcUtilsV2.freeConnection();
        }
        return rows;
    }


    /**
     * 将查询结果封转到一个实体类集合
     * @param clazz 要接值实体类集合的模板对象
     * @param sql  查询语句，要求列名或别名等于实体类的属性名
     * @param params  占位符的值要和占位符顺序一致
     * @return   查询的实体类集合
     * @param <T>   声明的结果的泛型
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */

    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... params) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        // 1、获取连接
        Connection connection = JDBCUtilsV2.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if(params != null && params.length > 0){
            for (int i = 1; i <= params.length; i++){
                preparedStatement.setObject(i,params[i-1]);
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> list = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()){
            T t = clazz.newInstance();
            for (int i = 1; i <= columnCount; i++){
                Object value = resultSet.getObject(i);
                String propertyName = metaData.getColumnLabel(i);
                Field field = clazz.getDeclaredField(propertyName);
                field.setAccessible(true);
                field.set(t,value);
            }
            list.add(t);
        }
        resultSet.close();
        preparedStatement.close();
        if(connection.getAutoCommit()){
            JDBCUtilsV2.freeConnection();
        }
        return list;
    }
}

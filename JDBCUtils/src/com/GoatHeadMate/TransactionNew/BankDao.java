package com.GoatHeadMate.TransactionNew;

import com.GoatHeadMate.Utils.JDBCUtils;
import com.GoatHeadMate.Utils.JDBCUtilsV2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author: Novigao
 * DATA: 2024/7/15-07-15-下午2:27
 * Description: com.novigao.transaction
 * Version: 1.0
 */
public class BankDao {
    /**
     * 加钱的数据库操作方法
     * @param account
     * @param money
     */
    public void add(String account, int money) throws SQLException {
        Connection connection = JDBCUtilsV2.getConnection();
        String sql="update t_bank set money=money+? where account=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,money);
        preparedStatement.setString(2,account);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println("加钱成功");
    }
    /**
     * 减钱的数据库操作方法
     * @param account
     * @param money
     */
    public void reduce(String account, int money) throws SQLException {
        Connection connection = JDBCUtilsV2.getConnection();
        String sql="update t_bank set money=money-? where account=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,money);
        preparedStatement.setString(2,account);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println("减钱成功");
    }
}

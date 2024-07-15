package com.GoatHeadMate.TransactionNew;

import com.GoatHeadMate.Utils.JDBCUtilsV2;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Novigao
 * DATA: 2024/7/15-07-15-下午2:37
 * Description: com.novigao.transaction
 * Version: 1.0
 */
public class BankService {
    public void transfer(String addAccount, String reduceAccount, int money) throws SQLException, ClassNotFoundException {
        BankDao bankDao = new BankDao();
        Connection connection = JDBCUtilsV2.getConnection();
        try{
            // 关闭自动提交
            connection.setAutoCommit(false);
            bankDao.add(addAccount, money);
            System.out.println("-----------");
            bankDao.reduce(reduceAccount, money);
            connection.commit();
        }catch (Exception e){
            connection.rollback();
            throw e;
        }finally {
            JDBCUtilsV2.freeConnection();
        }
    }

    @Test
    public void testTransfer() throws SQLException, ClassNotFoundException {
        transfer("lvdandan", "ergouzi", 500 );
    }
}

package com.GoatHeadMate.Test;



import com.GoatHeadMate.Utils.BaseDao;
import org.junit.jupiter.api.Test;

import java.sql.*;


/**
 * Author: Novigao
 * DATA: 2024/7/15-07-15-上午9:28
 * Description: com.novigao.PrepareStatement
 * Version: 1.0
 */
public class PSCURDPart extends BaseDao {
    @Test
    public void testInsert() throws SQLException {
        String sql = "insert into user(account,password,nickname) values(?,?,?);";
        int i = executeUpdate(sql, "sdlqgjy", "123456", "员工");
        if (i > 0)
        {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }
    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        String sql = "update user set nickname = ? where account = ?;";
        int i = executeUpdate(sql, "员工", "sdlqgjy");
        if(i > 0){
            System.out.println("更新成功");
        }
    }
    @Test
    public void testDelete() throws ClassNotFoundException, SQLException {

        // 3、获取预编译的Statement
        String sql = "delete from user where account = ?;";
        int sdlqgjy = executeUpdate(sql, "sdlqgjy");
        if(sdlqgjy > 0){
            System.out.println("删除成功");
        }
    }

}

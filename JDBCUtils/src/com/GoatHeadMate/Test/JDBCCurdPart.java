package com.GoatHeadMate.Test;

import com.GoatHeadMate.Utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: GoatHead Mate
 * DATA: 2024/7/15-07-15-下午4:53
 * Description: com.GoatHeadMate.Test
 * Version: 1.0
 */
public class JDBCCurdPart {
    @Test
    public void testInsert() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        connection.close();
    }
}

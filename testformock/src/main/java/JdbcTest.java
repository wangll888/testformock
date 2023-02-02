package java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {
    public static void main(String[] args) throws Throwable{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://mountclouds01.zgb.bjzt.360es.cn:3306/tengyunwork", "tengyun", "TengYun-2019@java.Test");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select count(*) as c from t_working_time");

        while (rs.next()) {
            System.out.println(rs.getInt(1));
        }
    }
}

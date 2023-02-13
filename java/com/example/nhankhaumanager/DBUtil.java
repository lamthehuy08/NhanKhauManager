package com.example.nhankhaumanager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil{
    public static Connection getConnection() throws ClassNotFoundException {
        Connection conn;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String url1 = "jdbc:sqlserver://LAPTOP-T77BEFIG:1433;instanceName = SQLEXPRESS;" +
                "databaseName=QuanLyNhanKhau_New;user=sa;password=thehuy21;encrypt=true;trustServerCertificate=true";
        String url = "jdbc:sqlserver://LAPTOP-T77BEFIG;" +
                "databaseName=QuanLyNhanKhau_New;user=sa;password=thehuy21;encrypt=true;trustServerCertificate=true;";


        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connect successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    public static void Close(Connection c){
        if(c!=null){
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

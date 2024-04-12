package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection connect() {
        Connection conn = null;
        final String DB_PATH = "C:\\Users\\HYUN\\Desktop\\Projects\\zb-mission1\\mission1.db";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("SQLite 데이터베이스 연결에 실패하였습니다: " + e.getMessage());
        }
        return conn;
    }

    public static void disconnect(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("SQLite 데이터베이스 연결 닫기 실패: " + e.getMessage());
            }
        }
    }
}

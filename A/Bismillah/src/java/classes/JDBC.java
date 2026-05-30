package classes;

import java.sql.*;

public class JDBC {
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public void connect() {
        String dbname = "test";
        String username = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname, username, password);
            message = "DB connected";
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    private void disconnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public int runUpdate(String query, Object... params) {
        int result = 0;
        try {
            connect();
            pstmt = con.prepareStatement(query);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            result = pstmt.executeUpdate();
            message = "info: " + result + " rows affected";
        } catch (Exception e) {
            message = e.getMessage();
        } finally {
            disconnect();
        }
        return result;
    }
}


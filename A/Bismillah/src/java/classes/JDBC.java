package classes;

import java.sql.*;

public class JDBC {
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "3306";
    private static final String DB_NAME = "klinik";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
            + "?useUnicode=true"
            + "&characterEncoding=UTF-8"
            + "&serverTimezone=UTC"
            + "&useSSL=false";

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public void connect() {
        try {
            Connection con = getConnection();
            con.close();
            message = "Database klinik berhasil terhubung";
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Class.forName("com.mysql.jdbc.Driver");
        }
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public int runUpdate(String query, Object... params) {
        int result = 0;
        try (Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {
            isiParameter(pstmt, params);
            result = pstmt.executeUpdate();
            message = "info: " + result + " rows affected";
        } catch (Exception e) {
            message = e.getMessage();
        }
        return result;
    }

    public int runInsert(String query, Object... params) {
        int id = 0;
        try (Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            isiParameter(pstmt, params);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
            message = "insert success";
        } catch (Exception e) {
            message = e.getMessage();
        }
        return id;
    }

    private void isiParameter(PreparedStatement pstmt, Object... params) throws SQLException {
        if (params == null) {
            return;
        }

        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
}

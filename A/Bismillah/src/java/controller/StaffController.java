package controller;

import classes.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StaffController extends BaseController {

    public List<Staff> getAllStaff() throws Exception {
        List<Staff> daftarStaff = new ArrayList<>();
        String sql = "SELECT u.id, u.nama, u.alamat, u.noHP "
                + "FROM `user` u JOIN staff s ON u.id = s.id "
                + "ORDER BY u.id";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                daftarStaff.add(new Staff(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("noHP")));
            }
        }

        return daftarStaff;
    }

    public int tambahStaff(String nama, String alamat, String noHP) throws Exception {
        validateText(nama, "Nama staff");
        validateText(alamat, "Alamat staff");
        validateText(noHP, "No HP staff");

        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            try {
                int id = insertUser(con, nama, alamat, noHP, "staff");
                try (PreparedStatement stmt = con.prepareStatement("INSERT INTO staff (id) VALUES (?)")) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }
                con.commit();
                return id;
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            }
        }
    }

    public boolean hapusStaff(int id) throws Exception {
        validatePositiveId(id, "ID staff");
        return executeUpdate("DELETE FROM `user` WHERE id = ?", id) > 0;
    }
}

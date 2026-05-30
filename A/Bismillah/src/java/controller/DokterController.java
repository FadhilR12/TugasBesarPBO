package controller;

import classes.Dokter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DokterController extends BaseController {

    public List<Dokter> getAllDokter() throws Exception {
        List<Dokter> daftarDokter = new ArrayList<>();
        String sql = "SELECT u.id, u.nama, u.alamat, u.noHP, d.spesialis "
                + "FROM `user` u JOIN dokter d ON u.id = d.id "
                + "ORDER BY u.id";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                daftarDokter.add(mapDokter(rs));
            }
        }

        return daftarDokter;
    }

    public Dokter getDokterById(int id) throws Exception {
        validatePositiveId(id, "ID dokter");
        String sql = "SELECT u.id, u.nama, u.alamat, u.noHP, d.spesialis "
                + "FROM `user` u JOIN dokter d ON u.id = d.id "
                + "WHERE u.id = ?";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapDokter(rs);
                }
            }
        }

        return null;
    }

    public int tambahDokter(String nama, String alamat, String noHP, String spesialis) throws Exception {
        validateText(nama, "Nama dokter");
        validateText(alamat, "Alamat dokter");
        validateText(noHP, "No HP dokter");
        validateText(spesialis, "Spesialis dokter");

        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            try {
                int id = insertUser(con, nama, alamat, noHP, "dokter");
                try (PreparedStatement stmt = con.prepareStatement(
                        "INSERT INTO dokter (id, spesialis) VALUES (?, ?)")) {
                    stmt.setInt(1, id);
                    stmt.setString(2, spesialis);
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

    public boolean hapusDokter(int id) throws Exception {
        validatePositiveId(id, "ID dokter");
        return executeUpdate("DELETE FROM `user` WHERE id = ?", id) > 0;
    }
}

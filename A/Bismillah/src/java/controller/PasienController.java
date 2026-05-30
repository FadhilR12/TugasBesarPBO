package controller;

import classes.Pasien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PasienController extends BaseController {

    public List<Pasien> getAllPasien() throws Exception {
        List<Pasien> daftarPasien = new ArrayList<>();
        String sql = "SELECT u.id, u.nama, u.alamat, u.noHP, p.noRekamMedis "
                + "FROM `user` u JOIN pasien p ON u.id = p.id "
                + "ORDER BY u.id";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                daftarPasien.add(mapPasien(rs));
            }
        }

        return daftarPasien;
    }

    public Pasien getPasienById(int id) throws Exception {
        validatePositiveId(id, "ID pasien");
        String sql = "SELECT u.id, u.nama, u.alamat, u.noHP, p.noRekamMedis "
                + "FROM `user` u JOIN pasien p ON u.id = p.id "
                + "WHERE u.id = ?";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPasien(rs);
                }
            }
        }

        return null;
    }

    public int tambahPasien(String nama, String alamat, String noHP, String noRekamMedis) throws Exception {
        validateText(nama, "Nama pasien");
        validateText(alamat, "Alamat pasien");
        validateText(noHP, "No HP pasien");
        validateText(noRekamMedis, "No rekam medis");

        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            try {
                int id = insertUser(con, nama, alamat, noHP, "pasien");
                try (PreparedStatement stmt = con.prepareStatement(
                        "INSERT INTO pasien (id, noRekamMedis) VALUES (?, ?)")) {
                    stmt.setInt(1, id);
                    stmt.setString(2, noRekamMedis);
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

    public boolean updatePasien(Pasien pasien) throws Exception {
        if (pasien == null) {
            throw new IllegalArgumentException("Data pasien tidak boleh kosong");
        }
        validatePositiveId(pasien.getId(), "ID pasien");
        validateText(pasien.getNama(), "Nama pasien");
        validateText(pasien.getAlamat(), "Alamat pasien");
        validateText(pasien.getNoHP(), "No HP pasien");
        validateText(pasien.getNoRekamMedis(), "No rekam medis");

        String updateUser = "UPDATE `user` SET nama = ?, alamat = ?, noHP = ? WHERE id = ?";
        String updatePasien = "UPDATE pasien SET noRekamMedis = ? WHERE id = ?";

        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            try {
                int affected;
                try (PreparedStatement stmt = con.prepareStatement(updateUser)) {
                    stmt.setString(1, pasien.getNama());
                    stmt.setString(2, pasien.getAlamat());
                    stmt.setString(3, pasien.getNoHP());
                    stmt.setInt(4, pasien.getId());
                    affected = stmt.executeUpdate();
                }

                try (PreparedStatement stmt = con.prepareStatement(updatePasien)) {
                    stmt.setString(1, pasien.getNoRekamMedis());
                    stmt.setInt(2, pasien.getId());
                    affected += stmt.executeUpdate();
                }

                con.commit();
                return affected > 0;
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            }
        }
    }

    public boolean hapusPasien(int id) throws Exception {
        validatePositiveId(id, "ID pasien");
        return executeUpdate("DELETE FROM `user` WHERE id = ?", id) > 0;
    }
}

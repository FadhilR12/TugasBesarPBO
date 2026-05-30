package controller;

import classes.Dokter;
import classes.JanjiTemu;
import classes.Pasien;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JanjiTemuController extends BaseController {

    public List<JanjiTemu> getAllJanjiTemu() throws Exception {
        List<JanjiTemu> daftarJanji = new ArrayList<>();
        String sql = "SELECT j.idJanji, j.tanggal, j.status, "
                + "du.id AS idDokter, du.nama AS namaDokter, du.alamat AS alamatDokter, du.noHP AS noHPDokter, d.spesialis, "
                + "pu.id AS idPasien, pu.nama AS namaPasien, pu.alamat AS alamatPasien, pu.noHP AS noHPPasien, p.noRekamMedis "
                + "FROM janjitemu j "
                + "JOIN dokter d ON j.idDokter = d.id "
                + "JOIN `user` du ON d.id = du.id "
                + "JOIN pasien p ON j.idPasien = p.id "
                + "JOIN `user` pu ON p.id = pu.id "
                + "ORDER BY j.tanggal";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dokter dokter = new Dokter(
                        rs.getInt("idDokter"),
                        rs.getString("namaDokter"),
                        rs.getString("alamatDokter"),
                        rs.getString("noHPDokter"),
                        rs.getString("spesialis"));
                Pasien pasien = new Pasien(
                        rs.getInt("idPasien"),
                        rs.getString("namaPasien"),
                        rs.getString("alamatPasien"),
                        rs.getString("noHPPasien"),
                        rs.getString("noRekamMedis"));
                daftarJanji.add(new JanjiTemu(
                        rs.getInt("idJanji"),
                        rs.getDate("tanggal").toString(),
                        rs.getString("status"),
                        dokter,
                        pasien));
            }
        }

        return daftarJanji;
    }

    public int tambahJanjiTemu(String tanggal, int idDokter, int idPasien) throws Exception {
        validatePositiveId(idDokter, "ID dokter");
        validatePositiveId(idPasien, "ID pasien");
        String sql = "INSERT INTO janjitemu (tanggal, status, idDokter, idPasien) VALUES (?, ?, ?, ?)";
        return executeInsert(sql, Date.valueOf(validateDate(tanggal)), "menunggu", idDokter, idPasien);
    }

    public boolean updateStatusJanji(int idJanji, String status) throws Exception {
        validatePositiveId(idJanji, "ID janji temu");
        validateText(status, "Status janji temu");
        validateStatusJanji(status);
        return executeUpdate("UPDATE janjitemu SET status = ? WHERE idJanji = ?", status, idJanji) > 0;
    }

    private void validateStatusJanji(String status) {
        if (!status.equals("menunggu") && !status.equals("selesai") && !status.equals("dibatalkan")) {
            throw new IllegalArgumentException("Status janji temu harus menunggu, selesai, atau dibatalkan");
        }
    }
}

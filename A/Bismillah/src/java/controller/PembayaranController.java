package controller;

import classes.Pembayaran;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PembayaranController extends BaseController {

    public List<Pembayaran> getAllPembayaran() throws Exception {
        List<Pembayaran> daftarPembayaran = new ArrayList<>();
        String sql = "SELECT idBayar, jumlah, status FROM pembayaran ORDER BY idBayar";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                daftarPembayaran.add(new Pembayaran(
                        rs.getInt("idBayar"),
                        rs.getDouble("jumlah"),
                        rs.getString("status")));
            }
        }

        return daftarPembayaran;
    }

    public int tambahPembayaran(double jumlah, int idJanji) throws Exception {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah pembayaran harus lebih dari 0");
        }
        validatePositiveId(idJanji, "ID janji temu");
        String sql = "INSERT INTO pembayaran (jumlah, status, idJanji) VALUES (?, ?, ?)";
        return executeInsert(sql, jumlah, "belum_bayar", idJanji);
    }

    public boolean prosesPembayaran(int idBayar) throws Exception {
        validatePositiveId(idBayar, "ID pembayaran");
        return executeUpdate("UPDATE pembayaran SET status = ? WHERE idBayar = ?", "lunas", idBayar) > 0;
    }
}

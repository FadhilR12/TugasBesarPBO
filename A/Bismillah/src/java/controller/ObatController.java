package controller;

import classes.Obat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ObatController extends BaseController {

    public List<Obat> getObatByResep(int idResep) throws Exception {
        validatePositiveId(idResep, "ID resep");
        List<Obat> daftarObat = new ArrayList<>();
        String sql = "SELECT idObat, namaObat, harga FROM obat WHERE idResep = ? ORDER BY idObat";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idResep);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    daftarObat.add(new Obat(
                            rs.getInt("idObat"),
                            rs.getString("namaObat"),
                            rs.getDouble("harga")));
                }
            }
        }

        return daftarObat;
    }

    public int tambahObat(String namaObat, double harga, int idResep) throws Exception {
        validateText(namaObat, "Nama obat");
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga obat harus lebih dari 0");
        }
        validatePositiveId(idResep, "ID resep");
        String sql = "INSERT INTO obat (namaObat, harga, idResep) VALUES (?, ?, ?)";
        return executeInsert(sql, namaObat, harga, idResep);
    }
}

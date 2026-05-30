package controller;

import classes.Obat;
import classes.Resep;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResepController extends BaseController {

    private final ObatController obatController;

    public ResepController() {
        this.obatController = new ObatController();
    }

    public List<Resep> getResepByPasien(int idPasien) throws Exception {
        validatePositiveId(idPasien, "ID pasien");
        List<Resep> daftarResep = new ArrayList<>();
        String sql = "SELECT idResep, tanggal FROM resep WHERE idPasien = ? ORDER BY tanggal";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idPasien);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Resep resep = new Resep(rs.getInt("idResep"), rs.getDate("tanggal").toString());
                    for (Obat obat : obatController.getObatByResep(resep.getIdResep())) {
                        resep.tambahObat(obat);
                    }
                    daftarResep.add(resep);
                }
            }
        }

        return daftarResep;
    }

    public int tambahResep(String tanggal, int idDokter, int idPasien) throws Exception {
        validatePositiveId(idDokter, "ID dokter");
        validatePositiveId(idPasien, "ID pasien");
        String sql = "INSERT INTO resep (tanggal, idDokter, idPasien) VALUES (?, ?, ?)";
        return executeInsert(sql, Date.valueOf(validateDate(tanggal)), idDokter, idPasien);
    }
}

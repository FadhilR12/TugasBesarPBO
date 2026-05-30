package controller;

import classes.RekamMedis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RekamMedisController extends BaseController {

    public List<RekamMedis> getRekamMedisByPasien(int idPasien) throws Exception {
        validatePositiveId(idPasien, "ID pasien");
        List<RekamMedis> daftarRekam = new ArrayList<>();
        String sql = "SELECT idRekam, diagnosa, tindakan FROM rekammedis WHERE idPasien = ? ORDER BY idRekam";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idPasien);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    daftarRekam.add(new RekamMedis(
                            rs.getInt("idRekam"),
                            rs.getString("diagnosa"),
                            rs.getString("tindakan")));
                }
            }
        }

        return daftarRekam;
    }

    public int tambahRekamMedis(String diagnosa, String tindakan, int idPasien, int idDokter) throws Exception {
        validateText(diagnosa, "Diagnosa");
        validateText(tindakan, "Tindakan");
        validatePositiveId(idPasien, "ID pasien");
        validatePositiveId(idDokter, "ID dokter");
        String sql = "INSERT INTO rekammedis (diagnosa, tindakan, idPasien, idDokter) VALUES (?, ?, ?, ?)";
        return executeInsert(sql, diagnosa, tindakan, idPasien, idDokter);
    }
}

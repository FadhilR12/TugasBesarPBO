package servlet;

import classes.JanjiTemu;
import classes.Pasien;
import classes.RekamMedis;
import classes.Resep;
import controller.JanjiTemuController;
import controller.PasienController;
import controller.RekamMedisController;
import controller.ResepController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "PasienServlet", urlPatterns = {"/pasien"})
public class PasienServlet extends BaseServlet {

    private final PasienController pasienController = new PasienController();
    private final JanjiTemuController janjiTemuController = new JanjiTemuController();
    private final RekamMedisController rekamMedisController = new RekamMedisController();
    private final ResepController resepController = new ResepController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String aksi = param(request, "aksi");
            if (aksi.equals("daftar")) {
                daftarPasien(request, response);
            } else if (aksi.equals("buatJanji")) {
                buatJanji(request, response);
            } else if (aksi.equals("riwayat")) {
                tampilkanRiwayat(request, response);
            } else if (aksi.equals("resep")) {
                tampilkanResep(request, response);
            } else {
                tampilkanPasien(response);
            }
        } catch (Exception ex) {
            writeError(response, ex);
        }
    }

    private void tampilkanPasien(HttpServletResponse response) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\" cellpadding=\"6\">");
        html.append("<tr><th>ID</th><th>Nama</th><th>Alamat</th><th>No HP</th><th>No Rekam Medis</th></tr>");
        for (Pasien pasien : pasienController.getAllPasien()) {
            html.append("<tr>");
            html.append("<td>").append(pasien.getId()).append("</td>");
            html.append("<td>").append(escape(pasien.getNama())).append("</td>");
            html.append("<td>").append(escape(pasien.getAlamat())).append("</td>");
            html.append("<td>").append(escape(pasien.getNoHP())).append("</td>");
            html.append("<td>").append(escape(pasien.getNoRekamMedis())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        writePage(response, "Data Pasien", html.toString());
    }

    private void daftarPasien(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String noRekamMedis = param(request, "noRekamMedis");
        if (noRekamMedis.isEmpty()) {
            noRekamMedis = "RM-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }

        int id = pasienController.tambahPasien(
                param(request, "nama"),
                param(request, "alamat"),
                param(request, "noHP"),
                noRekamMedis);

        writePage(response, "Pasien Terdaftar", "<p>Pasien berhasil didaftarkan dengan ID " + id + ".</p>");
    }

    private void buatJanji(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = janjiTemuController.tambahJanjiTemu(
                dateParam(request, "tanggal"),
                intParam(request, "idDokter"),
                intParam(request, "idPasien"));

        writePage(response, "Janji Temu Dibuat", "<p>Janji temu berhasil dibuat dengan ID " + id + ".</p>");
    }

    private void tampilkanRiwayat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\" cellpadding=\"6\">");
        html.append("<tr><th>ID</th><th>Diagnosa</th><th>Tindakan</th></tr>");
        for (RekamMedis rekam : rekamMedisController.getRekamMedisByPasien(intParam(request, "idPasien"))) {
            html.append("<tr>");
            html.append("<td>").append(rekam.getIdRekam()).append("</td>");
            html.append("<td>").append(escape(rekam.getDiagnosa())).append("</td>");
            html.append("<td>").append(escape(rekam.getTindakan())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        writePage(response, "Riwayat Pemeriksaan", html.toString());
    }

    private void tampilkanResep(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuilder html = new StringBuilder();
        for (Resep resep : resepController.getResepByPasien(intParam(request, "idPasien"))) {
            html.append("<h2>Resep #").append(resep.getIdResep()).append(" - ")
                    .append(escape(resep.getTanggal())).append("</h2>");
            html.append("<ul>");
            resep.getDaftarObat().forEach(obat ->
                    html.append("<li>").append(escape(obat.getNamaObat()))
                            .append(" - Rp").append(obat.getHarga()).append("</li>"));
            html.append("</ul>");
        }
        writePage(response, "Resep Pasien", html.toString());
    }
}

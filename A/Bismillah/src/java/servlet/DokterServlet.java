package servlet;

import classes.JanjiTemu;
import controller.JanjiTemuController;
import controller.RekamMedisController;
import controller.ResepController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DokterServlet", urlPatterns = {"/dokter"})
public class DokterServlet extends BaseServlet {

    private final JanjiTemuController janjiTemuController = new JanjiTemuController();
    private final RekamMedisController rekamMedisController = new RekamMedisController();
    private final ResepController resepController = new ResepController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String aksi = param(request, "aksi");
            if (aksi.equals("buatRekamMedis")) {
                buatRekamMedis(request, response);
            } else if (aksi.equals("buatResep")) {
                buatResep(request, response);
            } else {
                tampilkanJadwal(request, response);
            }
        } catch (Exception ex) {
            writeError(response, ex);
        }
    }

    private void tampilkanJadwal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idDokter = intParam(request, "idDokter");
        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\" cellpadding=\"6\">");
        html.append("<tr><th>ID Janji</th><th>Tanggal</th><th>Status</th><th>Pasien</th></tr>");
        for (JanjiTemu janji : janjiTemuController.getAllJanjiTemu()) {
            if (janji.getDokter() != null && janji.getDokter().getId() == idDokter) {
                html.append("<tr>");
                html.append("<td>").append(janji.getIdJanji()).append("</td>");
                html.append("<td>").append(escape(janji.getTanggal())).append("</td>");
                html.append("<td>").append(escape(janji.getStatus())).append("</td>");
                html.append("<td>").append(escape(janji.getPasien().getNama())).append("</td>");
                html.append("</tr>");
            }
        }
        html.append("</table>");
        writePage(response, "Jadwal Dokter", html.toString());
    }

    private void buatRekamMedis(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = rekamMedisController.tambahRekamMedis(
                param(request, "diagnosa"),
                param(request, "tindakan"),
                intParam(request, "idPasien"),
                intParam(request, "idDokter"));

        writePage(response, "Rekam Medis Dibuat", "<p>Rekam medis berhasil dibuat dengan ID " + id + ".</p>");
    }

    private void buatResep(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = resepController.tambahResep(
                dateParam(request, "tanggal"),
                intParam(request, "idDokter"),
                intParam(request, "idPasien"));

        writePage(response, "Resep Dibuat", "<p>Resep berhasil dibuat dengan ID " + id + ".</p>");
    }
}

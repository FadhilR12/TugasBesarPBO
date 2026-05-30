package servlet;

import classes.JanjiTemu;
import classes.Staff;
import controller.JanjiTemuController;
import controller.PembayaranController;
import controller.StaffController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StaffServlet", urlPatterns = {"/staff"})
public class StaffServlet extends BaseServlet {

    private final StaffController staffController = new StaffController();
    private final JanjiTemuController janjiTemuController = new JanjiTemuController();
    private final PembayaranController pembayaranController = new PembayaranController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String aksi = param(request, "aksi");
            if (aksi.equals("jadwal")) {
                tampilkanJadwal(response);
            } else if (aksi.equals("verifikasiPembayaran")) {
                verifikasiPembayaran(request, response);
            } else {
                dashboard(response);
            }
        } catch (Exception ex) {
            writeError(response, ex);
        }
    }

    private void dashboard(HttpServletResponse response) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<h2>Data Staff</h2>");
        html.append("<ul>");
        for (Staff staff : staffController.getAllStaff()) {
            html.append("<li>").append(staff.getId()).append(" - ")
                    .append(escape(staff.getNama())).append("</li>");
        }
        html.append("</ul>");
        writePage(response, "Dashboard Staff", html.toString());
    }

    private void tampilkanJadwal(HttpServletResponse response) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\" cellpadding=\"6\">");
        html.append("<tr><th>ID Janji</th><th>Tanggal</th><th>Status</th><th>Dokter</th><th>Pasien</th></tr>");
        for (JanjiTemu janji : janjiTemuController.getAllJanjiTemu()) {
            html.append("<tr>");
            html.append("<td>").append(janji.getIdJanji()).append("</td>");
            html.append("<td>").append(escape(janji.getTanggal())).append("</td>");
            html.append("<td>").append(escape(janji.getStatus())).append("</td>");
            html.append("<td>").append(escape(janji.getDokter().getNama())).append("</td>");
            html.append("<td>").append(escape(janji.getPasien().getNama())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        writePage(response, "Kelola Jadwal", html.toString());
    }

    private void verifikasiPembayaran(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean berhasil = pembayaranController.prosesPembayaran(intParam(request, "idBayar"));
        String message = berhasil ? "Pembayaran berhasil diverifikasi." : "Pembayaran tidak ditemukan.";
        writePage(response, "Verifikasi Pembayaran", "<p>" + escape(message) + "</p>");
    }
}

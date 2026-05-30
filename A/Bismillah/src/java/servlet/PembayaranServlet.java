package servlet;

import classes.Pembayaran;
import controller.PembayaranController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PembayaranServlet", urlPatterns = {"/pembayaran"})
public class PembayaranServlet extends BaseServlet {

    private final PembayaranController pembayaranController = new PembayaranController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String aksi = param(request, "aksi");
            if (aksi.equals("bayar")) {
                bayar(request, response);
            } else {
                tampilkanPembayaran(response);
            }
        } catch (Exception ex) {
            writeError(response, ex);
        }
    }

    private void tampilkanPembayaran(HttpServletResponse response) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\" cellpadding=\"6\">");
        html.append("<tr><th>ID Bayar</th><th>Jumlah</th><th>Status</th></tr>");
        for (Pembayaran pembayaran : pembayaranController.getAllPembayaran()) {
            html.append("<tr>");
            html.append("<td>").append(pembayaran.getIdBayar()).append("</td>");
            html.append("<td>").append(pembayaran.getJumlah()).append("</td>");
            html.append("<td>").append(escape(pembayaran.getStatus())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        writePage(response, "Data Pembayaran", html.toString());
    }

    private void bayar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = pembayaranController.tambahPembayaran(
                doubleParam(request, "jumlah"),
                intParam(request, "idJanji"));

        pembayaranController.prosesPembayaran(id);
        writePage(response, "Pembayaran Berhasil", "<p>Pembayaran berhasil dibuat dan diproses dengan ID " + id + ".</p>");
    }
}

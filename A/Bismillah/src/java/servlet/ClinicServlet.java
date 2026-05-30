package servlet;

import classes.*;
import controller.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.time.*;
import java.time.format.*;

@WebServlet(name = "ClinicServlet", urlPatterns = {"/pasien", "/dokter", "/staff", "/pembayaran"})
public class ClinicServlet extends HttpServlet {

    private final PasienController pasienController = new PasienController();
    private final JanjiTemuController janjiTemuController = new JanjiTemuController();
    private final RekamMedisController rekamMedisController = new RekamMedisController();
    private final ResepController resepController = new ResepController();
    private final StaffController staffController = new StaffController();
    private final PembayaranController pembayaranController = new PembayaranController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String path = request.getServletPath();
            if (path.equals("/pasien")) {
                handlePasien(request, response);
            } else if (path.equals("/dokter")) {
                handleDokter(request, response);
            } else if (path.equals("/staff")) {
                handleStaff(request, response);
            } else if (path.equals("/pembayaran")) {
                handlePembayaran(request, response);
            } else {
                writePage(response, "Halaman Tidak Ditemukan", "<p>URL tidak dikenali.</p>");
            }
        } catch (Exception ex) {
            writeError(response, ex);
        }
    }

    private void handlePasien(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aksi = param(request, "aksi");
        if (aksi.equals("daftar")) {
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
        } else if (aksi.equals("buatJanji")) {
            int id = janjiTemuController.tambahJanjiTemu(
                    dateParam(request, "tanggal"),
                    intParam(request, "idDokter"),
                    intParam(request, "idPasien"));
            writePage(response, "Janji Temu Dibuat", "<p>Janji temu berhasil dibuat dengan ID " + id + ".</p>");
        } else if (aksi.equals("riwayat")) {
            tampilkanRiwayatPasien(request, response);
        } else if (aksi.equals("resep")) {
            tampilkanResepPasien(request, response);
        } else {
            tampilkanPasien(response);
        }
    }

    private void handleDokter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aksi = param(request, "aksi");
        if (aksi.equals("buatRekamMedis")) {
            int id = rekamMedisController.tambahRekamMedis(
                    param(request, "diagnosa"),
                    param(request, "tindakan"),
                    intParam(request, "idPasien"),
                    intParam(request, "idDokter"));
            writePage(response, "Rekam Medis Dibuat", "<p>Rekam medis berhasil dibuat dengan ID " + id + ".</p>");
        } else if (aksi.equals("buatResep")) {
            int id = resepController.tambahResep(
                    dateParam(request, "tanggal"),
                    intParam(request, "idDokter"),
                    intParam(request, "idPasien"));
            writePage(response, "Resep Dibuat", "<p>Resep berhasil dibuat dengan ID " + id + ".</p>");
        } else {
            tampilkanJadwalDokter(request, response);
        }
    }

    private void handleStaff(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aksi = param(request, "aksi");
        if (aksi.equals("jadwal")) {
            tampilkanSemuaJadwal(response);
        } else if (aksi.equals("verifikasiPembayaran")) {
            boolean berhasil = pembayaranController.prosesPembayaran(intParam(request, "idBayar"));
            String message = berhasil ? "Pembayaran berhasil diverifikasi." : "Pembayaran tidak ditemukan.";
            writePage(response, "Verifikasi Pembayaran", "<p>" + escape(message) + "</p>");
        } else {
            tampilkanDashboardStaff(response);
        }
    }

    private void handlePembayaran(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aksi = param(request, "aksi");
        if (aksi.equals("bayar")) {
            int id = pembayaranController.tambahPembayaran(
                    doubleParam(request, "jumlah"),
                    intParam(request, "idJanji"));
            pembayaranController.prosesPembayaran(id);
            writePage(response, "Pembayaran Berhasil", "<p>Pembayaran berhasil dibuat dan diproses dengan ID " + id + ".</p>");
        } else {
            tampilkanPembayaran(response);
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

    private void tampilkanRiwayatPasien(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

    private void tampilkanResepPasien(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

    private void tampilkanJadwalDokter(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

    private void tampilkanDashboardStaff(HttpServletResponse response) throws Exception {
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

    private void tampilkanSemuaJadwal(HttpServletResponse response) throws Exception {
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

    private void writePage(HttpServletResponse response, String title, String body) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>" + escape(title) + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p><a href=\"index.html\">Kembali ke Home</a></p>");
            out.println("<h1>" + escape(title) + "</h1>");
            out.println(body);
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void writeError(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writePage(response, "Terjadi Kesalahan", "<p>" + escape(ex.getMessage()) + "</p>");
    }

    private String param(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? "" : value.trim();
    }

    private int intParam(HttpServletRequest request, String name) {
        return Integer.parseInt(param(request, name));
    }

    private double doubleParam(HttpServletRequest request, String name) {
        return Double.parseDouble(param(request, name));
    }

    private String dateParam(HttpServletRequest request, String name) {
        String value = param(request, name);
        if (value.isEmpty()) {
            return LocalDate.now().toString();
        }
        return value.length() >= 10 ? value.substring(0, 10) : value;
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}

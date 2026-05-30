package controller;

import classes.*;
import java.util.List;

public class ClinicController {

    private final PasienController pasienController;
    private final UserController userController;
    private final DokterController dokterController;
    private final StaffController staffController;
    private final JanjiTemuController janjiTemuController;
    private final PembayaranController pembayaranController;
    private final RekamMedisController rekamMedisController;
    private final ResepController resepController;
    private final ObatController obatController;

    public ClinicController() {
        this.pasienController = new PasienController();
        this.userController = new UserController();
        this.dokterController = new DokterController();
        this.staffController = new StaffController();
        this.janjiTemuController = new JanjiTemuController();
        this.pembayaranController = new PembayaranController();
        this.rekamMedisController = new RekamMedisController();
        this.resepController = new ResepController();
        this.obatController = new ObatController();
    }

    public List<Pasien> getAllPasien() throws Exception {
        return pasienController.getAllPasien();
    }

    public Pasien getPasienById(int id) throws Exception {
        return pasienController.getPasienById(id);
    }

    public int tambahPasien(String nama, String alamat, String noHP, String noRekamMedis) throws Exception {
        return pasienController.tambahPasien(nama, alamat, noHP, noRekamMedis);
    }

    public boolean updatePasien(Pasien pasien) throws Exception {
        return pasienController.updatePasien(pasien);
    }

    public List<Dokter> getAllDokter() throws Exception {
        return dokterController.getAllDokter();
    }

    public Dokter getDokterById(int id) throws Exception {
        return dokterController.getDokterById(id);
    }

    public int tambahDokter(String nama, String alamat, String noHP, String spesialis) throws Exception {
        return dokterController.tambahDokter(nama, alamat, noHP, spesialis);
    }

    public List<Staff> getAllStaff() throws Exception {
        return staffController.getAllStaff();
    }

    public int tambahStaff(String nama, String alamat, String noHP) throws Exception {
        return staffController.tambahStaff(nama, alamat, noHP);
    }

    public boolean hapusUser(int id) throws Exception {
        return userController.hapusUser(id);
    }

    public List<JanjiTemu> getAllJanjiTemu() throws Exception {
        return janjiTemuController.getAllJanjiTemu();
    }

    public int tambahJanjiTemu(String tanggal, int idDokter, int idPasien) throws Exception {
        return janjiTemuController.tambahJanjiTemu(tanggal, idDokter, idPasien);
    }

    public boolean updateStatusJanji(int idJanji, String status) throws Exception {
        return janjiTemuController.updateStatusJanji(idJanji, status);
    }

    public List<Pembayaran> getAllPembayaran() throws Exception {
        return pembayaranController.getAllPembayaran();
    }

    public int tambahPembayaran(double jumlah, int idJanji) throws Exception {
        return pembayaranController.tambahPembayaran(jumlah, idJanji);
    }

    public boolean prosesPembayaran(int idBayar) throws Exception {
        return pembayaranController.prosesPembayaran(idBayar);
    }

    public List<RekamMedis> getRekamMedisByPasien(int idPasien) throws Exception {
        return rekamMedisController.getRekamMedisByPasien(idPasien);
    }

    public int tambahRekamMedis(String diagnosa, String tindakan, int idPasien, int idDokter) throws Exception {
        return rekamMedisController.tambahRekamMedis(diagnosa, tindakan, idPasien, idDokter);
    }

    public List<Resep> getResepByPasien(int idPasien) throws Exception {
        return resepController.getResepByPasien(idPasien);
    }

    public int tambahResep(String tanggal, int idDokter, int idPasien) throws Exception {
        return resepController.tambahResep(tanggal, idDokter, idPasien);
    }

    public List<Obat> getObatByResep(int idResep) throws Exception {
        return obatController.getObatByResep(idResep);
    }

    public int tambahObat(String namaObat, double harga, int idResep) throws Exception {
        return obatController.tambahObat(namaObat, harga, idResep);
    }
}

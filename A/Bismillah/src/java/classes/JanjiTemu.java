package classes;

public class JanjiTemu {

    private int idJanji;
    private String tanggal;
    private String status;
    private Dokter dokter;
    private Pasien pasien;
    private Pembayaran pembayaran;

    public JanjiTemu(int idJanji, String tanggal, String status, Dokter dokter, Pasien pasien) {
        this.idJanji = idJanji;
        this.tanggal = tanggal;
        this.status = status;
        this.dokter = dokter;
        this.pasien = pasien;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }

    public int getIdJanji() {
        return idJanji;
    }

    public String getTanggal() {
        return tanggal;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public Pasien getPasien() {
        return pasien;
    }
}

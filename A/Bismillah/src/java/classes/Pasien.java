package classes;

import java.util.ArrayList;
import java.util.List;

public class Pasien extends User {

    private String noRekamMedis;
    private List<JanjiTemu> daftarJanji;
    private List<RekamMedis> daftarRekam;
    private List<Resep> daftarResep;

    public Pasien(int id, String nama, String alamat, String noHP) {
        super(id, nama, alamat, noHP);
        this.noRekamMedis = "";
        this.daftarJanji = new ArrayList<>();
        this.daftarRekam = new ArrayList<>();
        this.daftarResep = new ArrayList<>();
    }

    public void daftar() {
        // Implementasi pendaftaran pasien jika diperlukan
    }

    public void lihatRiwayat() {
        for (RekamMedis rm : daftarRekam) {
            rm.lihatRekam();
        }
    }

    public void lihatJanjiTemu() {
        for (JanjiTemu jt : daftarJanji) {
            System.out.println(jt.getStatus());
        }
    }

    public void buatJanji(JanjiTemu janji) {
        daftarJanji.add(janji);
    }

    public void batalkanJanji(JanjiTemu janji) {
        daftarJanji.remove(janji);
    }

    public void lihatPembayaran(Pembayaran pembayaran) {
        System.out.println("Jumlah pembayaran : " + pembayaran.getJumlah());
    }

    public void bayarTagihan() {
        System.out.println("Tagihan berhasil dibayar");
    }

    public void lihatResep() {
        for (Resep resep : daftarResep) {
            resep.lihatResep();
        }
    }

    public String getNoRekamMedis() {
        return noRekamMedis;
    }

    public List<JanjiTemu> getDaftarJanji() {
        return daftarJanji;
    }

    public List<RekamMedis> getDaftarRekam() {
        return daftarRekam;
    }

    public List<Resep> getDaftarResep() {
        return daftarResep;
    }
}

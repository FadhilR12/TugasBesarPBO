package classes;

import java.util.ArrayList;
import java.util.List;

public class Dokter extends User {

    private String spesialis;
    private List<JanjiTemu> daftarJanji;
    private List<Resep> daftarResep;

    public Dokter(int id, String nama, String alamat, String noHP, String spesialis) {
        super(id, nama, alamat, noHP);
        this.spesialis = spesialis;
        this.daftarJanji = new ArrayList<>();
        this.daftarResep = new ArrayList<>();
    }

    public void lihatJadwal() {
        for (JanjiTemu jt : daftarJanji) {
            System.out.println(jt.getStatus());
        }
    }

    public void tambahJanji(JanjiTemu janji) {
        daftarJanji.add(janji);
    }

    public void periksaPasien() {
        System.out.println("Pasien diperiksa");
    }

    public RekamMedis buatRekamMedis() {
        RekamMedis rekamMedis = new RekamMedis(0, "", "");
        return rekamMedis;
    }

    public RekamMedis buatRekamMedis(int idRekam, String diagnosa, String tindakan) {
        return new RekamMedis(idRekam, diagnosa, tindakan);
    }

    public Resep buatResep() {
        Resep resep = new Resep(0, "");
        daftarResep.add(resep);
        return resep;
    }

    public Resep buatResep(int idResep, String tanggal) {
        Resep resep = new Resep(idResep, tanggal);
        daftarResep.add(resep);
        return resep;
    }

    @Override
    public void login() {
        System.out.println("Dokter login");
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public List<JanjiTemu> getDaftarJanji() {
        return daftarJanji;
    }

    public List<Resep> getDaftarResep() {
        return daftarResep;
    }
}

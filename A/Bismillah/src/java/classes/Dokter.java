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

    public void periksaPasien() {
        System.out.println("Pasien diperiksa");
    }

    public RekamMedis buatRekamMedis(int idRekam, String diagnosa, String tindakan) {
        return new RekamMedis(idRekam, diagnosa, tindakan);
    }

    public Resep buatResep(int idResep, String tanggal) {
        Resep resep = new Resep(idResep, tanggal);
        daftarResep.add(resep);
        return resep;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public List<JanjiTemu> getDaftarJanji() {
        return daftarJanji;
    }
}

package classes;

import java.util.ArrayList;
import java.util.List;

public class Resep {

    private int idResep;
    private String tanggal;
    private List<Obat> daftarObat;

    public Resep(int idResep, String tanggal) {
        this.idResep = idResep;
        this.tanggal = tanggal;
        this.daftarObat = new ArrayList<>();
    }

    public void tambahObat(Obat obat) {
        daftarObat.add(obat);
    }

    public void lihatResep() {
        System.out.println("Daftar Obat:");
        for (Obat obat : daftarObat) {
            System.out.println(obat.getNamaObat());
        }
    }

    public int getIdResep() {
        return idResep;
    }

    public String getTanggal() {
        return tanggal;
    }

    public List<Obat> getDaftarObat() {
        return daftarObat;
    }
}

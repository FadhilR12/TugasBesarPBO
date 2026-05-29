package classes;

public class Obat {

    private int idObat;
    private String namaObat;
    private double harga;

    public Obat(int idObat, String namaObat, double harga) {
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.harga = harga;
    }

    public int getIdObat() {
        return idObat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public double getHarga() {
        return harga;
    }
}

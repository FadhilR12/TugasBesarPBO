package classes;

public class Pembayaran implements IBayar {

    private int idBayar;
    private double jumlah;
    private String status;

    public Pembayaran(int idBayar, double jumlah, String status) {
        this.idBayar = idBayar;
        this.jumlah = jumlah;
        this.status = status;
    }

    @Override
    public void prosesBayar() {
        status = "Lunas";
        System.out.println("Pembayaran diproses");
    }

    @Override
    public void konfirmasiPembayaran() {
        System.out.println("Pembayaran dikonfirmasi");
    }

    public int getIdBayar() {
        return idBayar;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }
}

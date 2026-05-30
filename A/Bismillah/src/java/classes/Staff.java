package classes;

public class Staff extends User {

    public Staff(int id, String nama, String alamat, String noHP) {
        super(id, nama, alamat, noHP);
    }

    public void kelolaDataPasien() {
        System.out.println("Mengelola data pasien");
    }

    public void kelolaJadwal() {
        System.out.println("Mengelola jadwal");
    }

    public void verifikasiPembayaran() {
        System.out.println("Pembayaran diverifikasi");
    }

    @Override
    public void login() {
        System.out.println("Staff login");
    }
}

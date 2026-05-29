package classes;

public class User {

    private int id;
    private String nama;
    private String alamat;
    private String noHP;

    public User(int id, String nama, String alamat, String noHP) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.noHP = noHP;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoHP() {
        return noHP;
    }

    public void login() {
        // Implement login logic if needed
    }

    public void logout() {
        // Implement logout logic if needed
    }
}

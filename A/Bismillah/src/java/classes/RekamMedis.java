package classes;

public class RekamMedis {

    private int idRekam;
    private String diagnosa;
    private String tindakan;

    public RekamMedis(int idRekam, String diagnosa, String tindakan) {
        this.idRekam = idRekam;
        this.diagnosa = diagnosa;
        this.tindakan = tindakan;
    }

    public void tambahRekam() {
        System.out.println("Rekam medis ditambahkan");
    }

    public void lihatRekam() {
        System.out.println("Diagnosa : " + diagnosa);
        System.out.println("Tindakan : " + tindakan);
    }

    public int getIdRekam() {
        return idRekam;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public String getTindakan() {
        return tindakan;
    }
}

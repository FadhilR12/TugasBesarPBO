package controller;

import classes.Dokter;
import classes.JDBC;
import classes.Pasien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

abstract class BaseController {

    protected final JDBC db;

    protected BaseController() {
        this.db = new JDBC();
    }

    protected int executeInsert(String sql, Object... params) throws Exception {
        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(stmt, params);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new Exception("Gagal mendapatkan id data baru");
    }

    protected int executeUpdate(String sql, Object... params) throws Exception {
        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            setParameters(stmt, params);
            return stmt.executeUpdate();
        }
    }

    protected void setParameters(PreparedStatement stmt, Object... params) throws Exception {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    protected int insertUser(Connection con, String nama, String alamat, String noHP, String role) throws Exception {
        String sql = "INSERT INTO `user` (nama, alamat, noHP, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nama);
            stmt.setString(2, alamat);
            stmt.setString(3, noHP);
            stmt.setString(4, role);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new Exception("Gagal mendapatkan id user baru");
    }

    protected Pasien mapPasien(ResultSet rs) throws Exception {
        return new Pasien(
                rs.getInt("id"),
                rs.getString("nama"),
                rs.getString("alamat"),
                rs.getString("noHP"),
                rs.getString("noRekamMedis"));
    }

    protected Dokter mapDokter(ResultSet rs) throws Exception {
        return new Dokter(
                rs.getInt("id"),
                rs.getString("nama"),
                rs.getString("alamat"),
                rs.getString("noHP"),
                rs.getString("spesialis"));
    }

    protected void validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " tidak boleh kosong");
        }
    }

    protected void validatePositiveId(int id, String fieldName) {
        if (id <= 0) {
            throw new IllegalArgumentException(fieldName + " harus lebih dari 0");
        }
    }

    protected LocalDate validateDate(String tanggal) {
        validateText(tanggal, "Tanggal");
        try {
            return LocalDate.parse(tanggal);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Format tanggal harus yyyy-MM-dd");
        }
    }
}

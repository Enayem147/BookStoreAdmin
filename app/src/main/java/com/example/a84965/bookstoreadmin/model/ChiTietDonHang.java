package com.example.a84965.bookstoreadmin.model;

public class ChiTietDonHang {
    private String DH_Ma;
    private String DH_NgayDat;
    private int DH_TrangThai;
    private String KH_SDT;

    public ChiTietDonHang() {
    }

    public ChiTietDonHang(String DH_Ma, String DH_NgayDat, int DH_TrangThai, String KH_SDT) {
        this.DH_Ma = DH_Ma;
        this.DH_NgayDat = DH_NgayDat;
        this.DH_TrangThai = DH_TrangThai;
        this.KH_SDT = KH_SDT;
    }

    public String getDH_Ma() {
        return DH_Ma;
    }

    public void setDH_Ma(String DH_Ma) {
        this.DH_Ma = DH_Ma;
    }

    public String getDH_NgayDat() {
        return DH_NgayDat;
    }

    public void setDH_NgayDat(String DH_NgayDat) {
        this.DH_NgayDat = DH_NgayDat;
    }

    public int getDH_TrangThai() {
        return DH_TrangThai;
    }

    public void setDH_TrangThai(int DH_TrangThai) {
        this.DH_TrangThai = DH_TrangThai;
    }

    public String getKH_SDT() {
        return KH_SDT;
    }

    public void setKH_SDT(String KH_SDT) {
        this.KH_SDT = KH_SDT;
    }
}

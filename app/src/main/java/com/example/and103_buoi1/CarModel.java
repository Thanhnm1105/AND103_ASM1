package com.example.and103_buoi1;


public class CarModel {
    private String _id;
    private String ten;
    private int namSX;
    private String hang;
    private double gia;
    private String anh;

    // Constructor với 6 tham số
    public CarModel(String _id, String ten, int namSX, String hang, double gia, String anh) {
        this._id = _id;
        this.ten = ten;
        this.namSX = namSX;
        this.hang = hang;
        this.gia = gia;
        this.anh = anh;
    }

    // Constructor với 5 tham số
    public CarModel(String ten, int namSX, String hang, double gia, String anh) {
        this.ten = ten;
        this.namSX = namSX;
        this.hang = hang;
        this.gia = gia;
        this.anh = anh;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getNamSX() {
        return namSX;
    }

    public void setNamSX(int namSX) {
        this.namSX = namSX;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    // Getter và setter...
}

package com.example.lan.moneyloverapi;

import java.util.Date;

/**
 * Created by Lan on 4/6/2018.
 */

public class InforMoney {
    private int id;
    private boolean thu;
    private String noidung;
    private int tien;
    private String ngay;

    public InforMoney(int id, boolean thu, String noidung, int tien, String ngay) {
        this.id = id;
        this.thu = thu;
        this.noidung = noidung;
        this.tien = tien;
        this.ngay = ngay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}

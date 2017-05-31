package com.mobilrandevu.Model;

public class RandevuPOJO {
    private int id;
    private String tarih;
    private String saat;
    private int ogrenciID;
    private int akademisyenID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public int getOgrenciID() {
        return ogrenciID;
    }

    public void setOgrenciID(int ogrenciID) {
        this.ogrenciID = ogrenciID;
    }

    public int getAkademisyenID() {
        return akademisyenID;
    }

    public void setAkademisyenID(int akademisyenID) {
        this.akademisyenID = akademisyenID;
    }
}

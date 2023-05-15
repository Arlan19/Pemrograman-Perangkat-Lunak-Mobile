package com.allacsta.firebaseloginandregis;

public class Channel {

    private String nama;
    private int nomor;
    private boolean status;
    private String url;

    public Channel() {
    }

    public Channel(String nama, int nomor, boolean status, String url) {
        this.nama = nama;
        this.nomor = nomor;
        this.status = status;
        this.url = url;
    }

    public String getNama() {
        return nama;
    }

    public int getNomor() {
        return nomor;
    }

    public boolean isStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "nama='" + nama + '\'' +
                ", nomor=" + nomor +
                ", status=" + status +
                ", url='" + url + '\'' +
                '}';
    }
}

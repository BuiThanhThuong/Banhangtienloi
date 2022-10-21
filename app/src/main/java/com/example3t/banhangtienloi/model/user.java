package com.example3t.banhangtienloi.model;



public class user {
    int IDuser;
    String SDT;
    String matkhau;
    String tenuser;
    String gmail;
    public user() {

    }

    public user(int IDuser) {
        this.IDuser = IDuser;
    }

    public int getIDuser() {
        return IDuser;
    }

    public void setIDuser(int IDuser) {
        this.IDuser = IDuser;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTenuser() {
        return tenuser;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public user(String SDT, String matkhau, String tenuser, String gmail) {
        this.SDT = SDT;
        this.matkhau = matkhau;
        this.tenuser = tenuser;
        this.gmail = gmail;

    }
}

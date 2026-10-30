package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sinh_vien")
public class SinhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long masv;

    @Column(nullable = false)
    private String tensv;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String email;

    @Column
    private String sdt;

    @Column
    private String lop;

    @Column
    private String password;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL)
    private List<PhieuMuon> phieuMuons = new ArrayList<>();

    public SinhVien() {}

    public SinhVien(String tensv) {
        this.tensv = tensv;
    }

    public Long getMasv() {
        return masv;
    }

    public void setMasv(Long masv) {
        this.masv = masv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhieuMuon> getPhieuMuons() {
        return phieuMuons;
    }

    public void setPhieuMuons(List<PhieuMuon> phieuMuons) {
        this.phieuMuons = phieuMuons;
    }
}

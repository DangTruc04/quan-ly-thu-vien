package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phieu_muon")
public class PhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maphieumuon;

    @Column(nullable = false)
    private LocalDate ngaylap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masv", nullable = false)
    private SinhVien sinhVien;

    @OneToMany(mappedBy = "phieuMuon", cascade = CascadeType.ALL)
    private List<ChiTietPhieuMuon> chiTietPhieuMuons = new ArrayList<>();

    @OneToOne(mappedBy = "phieuMuon", cascade = CascadeType.ALL)
    private PhieuTra phieuTra;

    public PhieuMuon() {}

    public PhieuMuon(LocalDate ngaylap, SinhVien sinhVien) {
        this.ngaylap = ngaylap;
        this.sinhVien = sinhVien;
    }

    public Long getMaphieumuon() {
        return maphieumuon;
    }

    public void setMaphieumuon(Long maphieumuon) {
        this.maphieumuon = maphieumuon;
    }

    public LocalDate getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(LocalDate ngaylap) {
        this.ngaylap = ngaylap;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public List<ChiTietPhieuMuon> getChiTietPhieuMuons() {
        return chiTietPhieuMuons;
    }

    public void setChiTietPhieuMuons(List<ChiTietPhieuMuon> chiTietPhieuMuons) {
        this.chiTietPhieuMuons = chiTietPhieuMuons;
    }

    public PhieuTra getPhieuTra() {
        return phieuTra;
    }

    public void setPhieuTra(PhieuTra phieuTra) {
        this.phieuTra = phieuTra;
    }
}

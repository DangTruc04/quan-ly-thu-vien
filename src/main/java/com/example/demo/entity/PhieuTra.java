package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "phieu_tra")
public class PhieuTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maphieutra;

    @Column(nullable = false)
    private LocalDate ngaylap;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maphieumuon", nullable = false)
    private PhieuMuon phieuMuon;

    public PhieuTra() {}

    public PhieuTra(LocalDate ngaylap, PhieuMuon phieuMuon) {
        this.ngaylap = ngaylap;
        this.phieuMuon = phieuMuon;
    }

    public Long getMaphieutra() {
        return maphieutra;
    }

    public void setMaphieutra(Long maphieutra) {
        this.maphieutra = maphieutra;
    }

    public LocalDate getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(LocalDate ngaylap) {
        this.ngaylap = ngaylap;
    }

    public PhieuMuon getPhieuMuon() {
        return phieuMuon;
    }

    public void setPhieuMuon(PhieuMuon phieuMuon) {
        this.phieuMuon = phieuMuon;
    }
}

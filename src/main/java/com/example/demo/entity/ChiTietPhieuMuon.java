package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chi_tiet_phieu_muon")
public class ChiTietPhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer soluongmuon = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maphieumuon", nullable = false)
    private PhieuMuon phieuMuon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masach", nullable = false)
    private Sach sach;

    public ChiTietPhieuMuon() {}

    public ChiTietPhieuMuon(Integer soluongmuon, PhieuMuon phieuMuon, Sach sach) {
        this.soluongmuon = soluongmuon;
        this.phieuMuon = phieuMuon;
        this.sach = sach;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSoluongmuon() {
        return soluongmuon;
    }

    public void setSoluongmuon(Integer soluongmuon) {
        this.soluongmuon = soluongmuon;
    }

    public PhieuMuon getPhieuMuon() {
        return phieuMuon;
    }

    public void setPhieuMuon(PhieuMuon phieuMuon) {
        this.phieuMuon = phieuMuon;
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }
}

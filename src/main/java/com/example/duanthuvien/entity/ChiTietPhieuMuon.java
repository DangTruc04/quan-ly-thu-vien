package com.example.duanthuvien.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chi_tiet_phieu_muon")
public class ChiTietPhieuMuon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maphieumuon")
    private PhieuMuon phieuMuon;

    @ManyToOne
    @JoinColumn(name = "masach")
    private Sach sach;

    private Integer soluongmuon;

    public ChiTietPhieuMuon() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public PhieuMuon getPhieuMuon() { return phieuMuon; }
    public void setPhieuMuon(PhieuMuon phieuMuon) { this.phieuMuon = phieuMuon; }

    public Sach getSach() { return sach; }
    public void setSach(Sach sach) { this.sach = sach; }

    public Integer getSoluongmuon() { return soluongmuon; }
    public void setSoluongmuon(Integer soluongmuon) { this.soluongmuon = soluongmuon; }
}
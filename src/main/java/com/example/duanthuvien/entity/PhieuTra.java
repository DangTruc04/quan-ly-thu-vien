package com.example.duanthuvien.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieu_tra")
public class PhieuTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maphieutra;

    private LocalDateTime ngaylap;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String ghichu;

    @ManyToOne
    @JoinColumn(name = "maphieumuon")
    private PhieuMuon phieuMuon;

    public PhieuTra() {}

    public Integer getMaphieutra() { return maphieutra; }
    public void setMaphieutra(Integer maphieutra) { this.maphieutra = maphieutra; }

    public LocalDateTime getNgaylap() { return ngaylap; }
    public void setNgaylap(LocalDateTime ngaylap) { this.ngaylap = ngaylap; }

    public String getGhichu() { return ghichu; }
    public void setGhichu(String ghichu) { this.ghichu = ghichu; }

    public PhieuMuon getPhieuMuon() { return phieuMuon; }
    public void setPhieuMuon(PhieuMuon phieuMuon) { this.phieuMuon = phieuMuon; }
}
package com.example.duanthuvien.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieu_muon")
public class PhieuMuon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maphieumuon;

    private LocalDateTime ngaylap;

    @ManyToOne
    @JoinColumn(name = "masv")
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "idtk")
    private TaiKhoan taiKhoan;

    public PhieuMuon() {}

    public Integer getMaphieumuon() { return maphieumuon; }
    public void setMaphieumuon(Integer maphieumuon) { this.maphieumuon = maphieumuon; }

    public LocalDateTime getNgaylap() { return ngaylap; }
    public void setNgaylap(LocalDateTime ngaylap) { this.ngaylap = ngaylap; }

    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }

    public TaiKhoan getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(TaiKhoan taiKhoan) { this.taiKhoan = taiKhoan; }
}
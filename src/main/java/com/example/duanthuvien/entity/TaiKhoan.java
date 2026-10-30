package com.example.duanthuvien.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tai_khoan")
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtk;

    private String tentk;

    @Column(nullable = false, unique = true)
    private String taikhoan;

    @Column(nullable = false)
    private String matkhau;

    @OneToMany(mappedBy = "taiKhoan")
    private List<PhieuMuon> phieuMuons;

    @OneToMany(mappedBy = "taiKhoan")
    private List<PhieuTra> phieuTras;

    public TaiKhoan() {}

    public Integer getIdtk() { return idtk; }
    public void setIdtk(Integer idtk) { this.idtk = idtk; }

    public String getTentk() { return tentk; }
    public void setTentk(String tentk) { this.tentk = tentk; }

    public String getTaikhoan() { return taikhoan; }
    public void setTaikhoan(String taikhoan) { this.taikhoan = taikhoan; }

    public String getMatkhau() { return matkhau; }
    public void setMatkhau(String matkhau) { this.matkhau = matkhau; }

    public List<PhieuMuon> getPhieuMuons() { return phieuMuons; }
    public void setPhieuMuons(List<PhieuMuon> phieuMuons) { this.phieuMuons = phieuMuons; }

    public List<PhieuTra> getPhieuTras() { return phieuTras; }
    public void setPhieuTras(List<PhieuTra> phieuTras) { this.phieuTras = phieuTras; }
}
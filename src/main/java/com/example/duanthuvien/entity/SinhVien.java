package com.example.duanthuvien.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sinh_vien")
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masv;

    @Column(nullable = false)
    private String tensv;

    private LocalDate ngaysinh;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL)
    private List<PhieuMuon> phieuMuons;

    public SinhVien() {}

    public Integer getMasv() { return masv; }
    public void setMasv(Integer masv) { this.masv = masv; }

    public String getTensv() { return tensv; }
    public void setTensv(String tensv) { this.tensv = tensv; }

    public LocalDate getNgaysinh() { return ngaysinh; }
    public void setNgaysinh(LocalDate ngaysinh) { this.ngaysinh = ngaysinh; }

    public List<PhieuMuon> getPhieuMuons() { return phieuMuons; }
    public void setPhieuMuons(List<PhieuMuon> phieuMuons) { this.phieuMuons = phieuMuons; }
}
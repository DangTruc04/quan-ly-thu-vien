package com.example.duanthuvien.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sinh_vien")
public class SinhVien {

    @Id
    @Column(name = "masv")
    private Integer masv;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String tensv;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhieuMuon> phieuMuons;

    public SinhVien() {}

    public Integer getMasv() { return masv; }
    public void setMasv(Integer masv) { this.masv = masv; }

    public String getTensv() { return tensv; }
    public void setTensv(String tensv) { this.tensv = tensv; }

    public List<PhieuMuon> getPhieuMuons() { return phieuMuons; }
    public void setPhieuMuons(List<PhieuMuon> phieuMuons) { this.phieuMuons = phieuMuons; }
}
package com.example.duanthuvien.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phieu_muon")
public class PhieuMuon {

    /** Số ngày mượn mặc định. */
    public static final int SO_NGAY_MUON_MAC_DINH = 14;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maphieumuon;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngaylap;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayhentra;

    @ManyToOne
    @JoinColumn(name = "masv")
    private SinhVien sinhVien;

    @OneToMany(mappedBy = "phieuMuon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietPhieuMuon> chiTietPhieuMuons = new ArrayList<>();

    public PhieuMuon() {}

    public Integer getMaphieumuon() { return maphieumuon; }
    public void setMaphieumuon(Integer maphieumuon) { this.maphieumuon = maphieumuon; }

    public LocalDate getNgaylap() { return ngaylap; }
    public void setNgaylap(LocalDate ngaylap) { this.ngaylap = ngaylap; }

    public LocalDate getNgayhentra() { return ngayhentra; }
    public void setNgayhentra(LocalDate ngayhentra) { this.ngayhentra = ngayhentra; }

    /** Số ngày còn lại tính từ hôm nay, âm nếu đã quá hạn. */
    @Transient
    public Long getSoNgayConLai() {
        if (ngayhentra == null) {
            return null;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), ngayhentra);
    }

    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }

    public List<ChiTietPhieuMuon> getChiTietPhieuMuons() { return chiTietPhieuMuons; }
    public void setChiTietPhieuMuons(List<ChiTietPhieuMuon> chiTietPhieuMuons) {
        this.chiTietPhieuMuons = chiTietPhieuMuons;
    }
}

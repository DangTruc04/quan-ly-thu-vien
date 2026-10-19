package com.example.duanthuvien.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "loai_sach")
public class LoaiSach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maloai;

    @Column(nullable = false)
    private String tenloai;

    private Integer trangthai;

    @OneToMany(mappedBy = "loaiSach", cascade = CascadeType.ALL)
    private List<Sach> sachs;

    public LoaiSach() {}

    public Integer getMaloai() { return maloai; }
    public void setMaloai(Integer maloai) { this.maloai = maloai; }

    public String getTenloai() { return tenloai; }
    public void setTenloai(String tenloai) { this.tenloai = tenloai; }

    public Integer getTrangthai() { return trangthai; }
    public void setTrangthai(Integer trangthai) { this.trangthai = trangthai; }

    public List<Sach> getSachs() { return sachs; }
    public void setSachs(List<Sach> sachs) { this.sachs = sachs; }
}
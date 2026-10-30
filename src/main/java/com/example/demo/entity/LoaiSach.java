package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loai_sach")
public class LoaiSach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maloai;

    @Column(nullable = false, unique = true)
    private String tenloai;

    @Column
    private String trangthai = "Active";

    @OneToMany(mappedBy = "loaiSach", cascade = CascadeType.ALL)
    private List<Sach> sachs = new ArrayList<>();

    public LoaiSach() {}

    public LoaiSach(String tenloai) {
        this.tenloai = tenloai;
    }

    public Long getMaloai() {
        return maloai;
    }

    public void setMaloai(Long maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public List<Sach> getSachs() {
        return sachs;
    }

    public void setSachs(List<Sach> sachs) {
        this.sachs = sachs;
    }
}

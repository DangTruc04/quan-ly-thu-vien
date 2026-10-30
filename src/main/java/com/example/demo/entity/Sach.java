package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sach")
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long masach;

    @Column(nullable = false)
    private String tensach;

    @Column(nullable = false)
    private Integer soluong = 0;

    @Column
    private Integer taiban = 0;

    @Column
    private String trangthai = "Available";

    @Column
    private String imageUrl;

    @Column
    private Double gia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maloai")
    private LoaiSach loaiSach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tacgia_id")
    private Author author;

    @OneToMany(mappedBy = "sach", cascade = CascadeType.ALL)
    private List<ChiTietPhieuMuon> chiTietPhieuMuons = new ArrayList<>();

    public Sach() {}

    public Sach(String tensach, Integer soluong) {
        this.tensach = tensach;
        this.soluong = soluong;
    }

    public Long getMasach() {
        return masach;
    }

    public void setMasach(Long masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Integer getTaiban() {
        return taiban;
    }

    public void setTaiban(Integer taiban) {
        this.taiban = taiban;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public LoaiSach getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(LoaiSach loaiSach) {
        this.loaiSach = loaiSach;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<ChiTietPhieuMuon> getChiTietPhieuMuons() {
        return chiTietPhieuMuons;
    }

    public void setChiTietPhieuMuons(List<ChiTietPhieuMuon> chiTietPhieuMuons) {
        this.chiTietPhieuMuons = chiTietPhieuMuons;
    }
}

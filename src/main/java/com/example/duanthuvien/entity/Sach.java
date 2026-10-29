package com.example.duanthuvien.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sach")
public class Sach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masach;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String tensach;

    private Integer soluong;
    private Integer sachcon;
    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "maloai")
    private LoaiSach loaiSach;

    public Sach() {}

    public Integer getMasach() { return masach; }
    public void setMasach(Integer masach) { this.masach = masach; }

    public String getTensach() { return tensach; }
    public void setTensach(String tensach) { this.tensach = tensach; }

    public Integer getSoluong() { return soluong; }
    public void setSoluong(Integer soluong) { this.soluong = soluong; }

    public Integer getSachcon() { return sachcon; }
    public void setSachcon(Integer sachcon) { this.sachcon = sachcon; }

    public Integer getTrangthai() { return trangthai; }
    public void setTrangthai(Integer trangthai) { this.trangthai = trangthai; }

    public LoaiSach getLoaiSach() { return loaiSach; }
    public void setLoaiSach(LoaiSach loaiSach) { this.loaiSach = loaiSach; }
}
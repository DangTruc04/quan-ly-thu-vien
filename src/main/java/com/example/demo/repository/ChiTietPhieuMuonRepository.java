package com.example.demo.repository;

import com.example.demo.entity.ChiTietPhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietPhieuMuonRepository extends JpaRepository<ChiTietPhieuMuon, Long> {
    List<ChiTietPhieuMuon> findByPhieuMuon_Maphieumuon(Long maphieumuon);
}

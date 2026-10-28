package com.example.duanthuvien.repository;

import com.example.duanthuvien.entity.ChiTietPhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuMuonRepository
        extends JpaRepository<ChiTietPhieuMuon, Integer> {

    long countBySachMasach(Integer masach);
}

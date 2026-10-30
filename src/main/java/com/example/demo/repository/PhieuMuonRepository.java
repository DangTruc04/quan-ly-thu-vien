package com.example.demo.repository;

import com.example.demo.entity.PhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, Long> {
    List<PhieuMuon> findBySinhVien_Masv(Long masv);
}

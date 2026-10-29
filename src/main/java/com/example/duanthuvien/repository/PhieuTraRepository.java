package com.example.duanthuvien.repository;

import com.example.duanthuvien.entity.PhieuTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuTraRepository extends JpaRepository<PhieuTra, Integer> {

    long countByPhieuMuonMaphieumuon(Integer maphieumuon);

    List<PhieuTra> findAllByOrderByMaphieutraAsc();
}

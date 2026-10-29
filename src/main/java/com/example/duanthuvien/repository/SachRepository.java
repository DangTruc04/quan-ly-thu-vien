package com.example.duanthuvien.repository;

import com.example.duanthuvien.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SachRepository extends JpaRepository<Sach, Integer> {

    boolean existsByTensach(String tensach);

    boolean existsByTensachAndMasachNot(String tensach, Integer masach);

    long countByLoaiSachMaloai(Integer maloai);

    List<Sach> findBySachconGreaterThan(Integer sachcon);
}

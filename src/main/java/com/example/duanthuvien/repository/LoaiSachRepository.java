package com.example.duanthuvien.repository;

import com.example.duanthuvien.entity.LoaiSach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiSachRepository extends JpaRepository<LoaiSach, Integer> {

    boolean existsByTenloai(String tenloai);

    boolean existsByTenloaiAndMaloaiNot(String tenloai, Integer maloai);
}
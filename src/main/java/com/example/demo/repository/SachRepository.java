package com.example.demo.repository;

import com.example.demo.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SachRepository extends JpaRepository<Sach, Long> {
    List<Sach> findByLoaiSach_Maloai(Long maloai);
    
    @Query("SELECT s FROM Sach s WHERE s.tensach LIKE %:keyword%")
    List<Sach> searchByTenSach(@Param("keyword") String keyword);
}

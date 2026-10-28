package com.example.duanthuvien.service;

import com.example.duanthuvien.entity.LoaiSach;
import com.example.duanthuvien.repository.LoaiSachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiSachService {

    @Autowired
    private LoaiSachRepository loaiSachRepository;

    public List<LoaiSach> getAllLoaiSach() {
        return loaiSachRepository.findAll();
    }

    public long countLoaiSach() {
        return loaiSachRepository.count();
    }

    public Optional<LoaiSach> findById(Integer id) {
        return loaiSachRepository.findById(id);
    }

    public LoaiSach save(LoaiSach loaiSach) {
        return loaiSachRepository.save(loaiSach);
    }

    public void deleteById(Integer id) {
        loaiSachRepository.deleteById(id);
    }

    public boolean existsByTenloai(String tenloai) {
        return loaiSachRepository.existsByTenloai(tenloai);
    }

    public boolean existsByTenloaiAndMaloaiNot(String tenloai, Integer maloai) {
        return loaiSachRepository.existsByTenloaiAndMaloaiNot(tenloai, maloai);
    }
}

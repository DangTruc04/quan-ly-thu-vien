package com.example.duanthuvien.service;

import com.example.duanthuvien.entity.SinhVien;
import com.example.duanthuvien.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    public List<SinhVien> getAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    public long countSinhVien() {
        return sinhVienRepository.count();
    }

    public Optional<SinhVien> findById(Integer id) {
        return sinhVienRepository.findById(id);
    }

    public SinhVien save(SinhVien sinhVien) {
        return sinhVienRepository.save(sinhVien);
    }

    public void deleteById(Integer id) {
        sinhVienRepository.deleteById(id);
    }
}
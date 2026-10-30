package com.example.demo.service;

import com.example.demo.entity.Sach;
import com.example.demo.repository.SachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SachService {
    @Autowired
    private SachRepository sachRepository;

    public List<Sach> getAllSach() {
        return sachRepository.findAll();
    }

    public Sach getSachById(Long masach) {
        return sachRepository.findById(masach).orElse(null);
    }

    public List<Sach> getSachByLoai(Long maloai) {
        return sachRepository.findByLoaiSach_Maloai(maloai);
    }

    public List<Sach> searchSach(String keyword) {
        return sachRepository.searchByTenSach(keyword);
    }

    public Sach saveSach(Sach sach) {
        return sachRepository.save(sach);
    }

    public void deleteSach(Long masach) {
        sachRepository.deleteById(masach);
    }
}

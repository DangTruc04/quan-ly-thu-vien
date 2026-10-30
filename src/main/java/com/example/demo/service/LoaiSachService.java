package com.example.demo.service;

import com.example.demo.entity.LoaiSach;
import com.example.demo.repository.LoaiSachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiSachService {
    @Autowired
    private LoaiSachRepository loaiSachRepository;

    public List<LoaiSach> getAllLoaiSach() {
        return loaiSachRepository.findAll();
    }

    public LoaiSach getLoaiSachById(Long maloai) {
        return loaiSachRepository.findById(maloai).orElse(null);
    }

    public LoaiSach saveLoaiSach(LoaiSach loaiSach) {
        return loaiSachRepository.save(loaiSach);
    }

    public void deleteLoaiSach(Long maloai) {
        loaiSachRepository.deleteById(maloai);
    }
}

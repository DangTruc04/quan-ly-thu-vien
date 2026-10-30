package com.example.duanthuvien.service;

import com.example.duanthuvien.entity.Sach;
import com.example.duanthuvien.repository.SachRepository;
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

    public long countSach() {
        return sachRepository.count();
    }

    public void saveSach(Sach sach) {
        sachRepository.save(sach);
    }
}
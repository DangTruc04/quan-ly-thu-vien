package com.example.duanthuvien.service;

import com.example.duanthuvien.repository.PhieuMuonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhieuMuonService {

    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    public long countPhieuMuon() {
        return phieuMuonRepository.count();
    }
}
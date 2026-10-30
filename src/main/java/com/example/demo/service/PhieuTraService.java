package com.example.demo.service;

import com.example.demo.entity.ChiTietPhieuMuon;
import com.example.demo.entity.PhieuMuon;
import com.example.demo.entity.PhieuTra;
import com.example.demo.entity.Sach;
import com.example.demo.repository.ChiTietPhieuMuonRepository;
import com.example.demo.repository.PhieuTraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PhieuTraService {
    @Autowired
    private PhieuTraRepository phieuTraRepository;

    @Autowired
    private ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;

    @Autowired
    private SachService sachService;

    @Transactional
    public PhieuTra createPhieuTra(Long maphieumuon) {
        PhieuMuon phieuMuon = new PhieuMuon();
        phieuMuon.setMaphieumuon(maphieumuon);
        
        PhieuTra phieuTra = new PhieuTra(LocalDate.now(), phieuMuon);
        phieuTra = phieuTraRepository.save(phieuTra);

        List<ChiTietPhieuMuon> chiTiets = chiTietPhieuMuonRepository.findByPhieuMuon_Maphieumuon(maphieumuon);
        for (ChiTietPhieuMuon chiTiet : chiTiets) {
            Sach sach = chiTiet.getSach();
            sach.setSoluong(sach.getSoluong() + chiTiet.getSoluongmuon());
            sachService.saveSach(sach);
        }

        return phieuTra;
    }

    public List<PhieuTra> getAllPhieuTra() {
        return phieuTraRepository.findAll();
    }

    public PhieuTra getPhieuTraById(Long maphieutra) {
        return phieuTraRepository.findById(maphieutra).orElse(null);
    }
}

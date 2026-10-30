package com.example.demo.service;

import com.example.demo.entity.ChiTietPhieuMuon;
import com.example.demo.entity.PhieuMuon;
import com.example.demo.entity.Sach;
import com.example.demo.entity.SinhVien;
import com.example.demo.repository.ChiTietPhieuMuonRepository;
import com.example.demo.repository.PhieuMuonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PhieuMuonService {
    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @Autowired
    private ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;

    @Autowired
    private SachService sachService;

    @Transactional
    public PhieuMuon createPhieuMuon(SinhVien sinhVien, List<Long> sachIds, List<Integer> soLuongs) {
        PhieuMuon phieuMuon = new PhieuMuon(LocalDate.now(), sinhVien);
        phieuMuon = phieuMuonRepository.save(phieuMuon);

        for (int i = 0; i < sachIds.size(); i++) {
            Long sachId = sachIds.get(i);
            Integer soLuong = soLuongs.get(i);
            
            Sach sach = sachService.getSachById(sachId);
            if (sach != null && sach.getSoluong() >= soLuong) {
                ChiTietPhieuMuon chiTiet = new ChiTietPhieuMuon(soLuong, phieuMuon, sach);
                chiTietPhieuMuonRepository.save(chiTiet);
                
                sach.setSoluong(sach.getSoluong() - soLuong);
                sachService.saveSach(sach);
            }
        }

        return phieuMuon;
    }

    public List<PhieuMuon> getAllPhieuMuon() {
        return phieuMuonRepository.findAll();
    }

    public List<PhieuMuon> getPhieuMuonBySinhVien(Long masv) {
        return phieuMuonRepository.findBySinhVien_Masv(masv);
    }

    public PhieuMuon getPhieuMuonById(Long maphieumuon) {
        return phieuMuonRepository.findById(maphieumuon).orElse(null);
    }
}

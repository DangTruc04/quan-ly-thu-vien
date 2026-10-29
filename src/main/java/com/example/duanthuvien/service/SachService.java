package com.example.duanthuvien.service;

import com.example.duanthuvien.entity.Sach;
import com.example.duanthuvien.repository.ChiTietPhieuMuonRepository;
import com.example.duanthuvien.repository.SachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SachService {

    @Autowired
    private SachRepository sachRepository;

    @Autowired
    private ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;

    public List<Sach> getAllSach() {
        return sachRepository.findAll();
    }

    /** Chỉ những sách còn số lượng mới được chọn khi lập phiếu mượn. */
    public List<Sach> getSachCoTheMuon() {
        return sachRepository.findBySachconGreaterThan(0);
    }

    public long countSach() {
        return sachRepository.count();
    }

    public Optional<Sach> findById(Integer id) {
        return sachRepository.findById(id);
    }

    public Sach save(Sach sach) {
        if (sach.getSachcon() == null) {
            sach.setSachcon(sach.getSoluong() == null ? 0 : sach.getSoluong());
        }
        return sachRepository.save(sach);
    }

    public void deleteById(Integer id) {
        sachRepository.deleteById(id);
    }

    public boolean existsByTensach(String tensach) {
        return sachRepository.existsByTensach(tensach);
    }

    public boolean existsByTensachAndMasachNot(String tensach, Integer masach) {
        return sachRepository.existsByTensachAndMasachNot(tensach, masach);
    }

    public long countByLoai(Integer maloai) {
        return sachRepository.countByLoaiSachMaloai(maloai);
    }

    public long countChiTietBySach(Integer masach) {
        return chiTietPhieuMuonRepository.countBySachMasach(masach);
    }
}

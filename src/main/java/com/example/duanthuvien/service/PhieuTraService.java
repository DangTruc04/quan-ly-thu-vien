package com.example.duanthuvien.service;

import com.example.duanthuvien.entity.ChiTietPhieuMuon;
import com.example.duanthuvien.entity.PhieuMuon;
import com.example.duanthuvien.entity.PhieuTra;
import com.example.duanthuvien.entity.Sach;
import com.example.duanthuvien.repository.PhieuMuonRepository;
import com.example.duanthuvien.repository.PhieuTraRepository;
import com.example.duanthuvien.repository.SachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuTraService {

    @Autowired
    private PhieuTraRepository phieuTraRepository;

    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @Autowired
    private SachRepository sachRepository;

    public List<PhieuTra> getAll() {
        return phieuTraRepository.findAllByOrderByMaphieutraAsc();
    }

    public Optional<PhieuTra> findById(Integer id) {
        return phieuTraRepository.findById(id);
    }

    @Transactional
    public PhieuTra taoPhieuTra(Integer maphieumuon, LocalDate ngayTra, String ghiChu) {
        if (ngayTra == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày trả.");
        }
        if (phieuTraRepository.countByPhieuMuonMaphieumuon(maphieumuon) > 0) {
            throw new IllegalArgumentException("Phiếu mượn này đã có phiếu trả.");
        }

        PhieuMuon phieuMuon = phieuMuonRepository.findById(maphieumuon)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu mượn."));

        for (ChiTietPhieuMuon chiTiet : phieuMuon.getChiTietPhieuMuons()) {
            if (chiTiet.getSach() == null || chiTiet.getSach().getMasach() == null) continue;

            Sach sach = sachRepository.findById(chiTiet.getSach().getMasach())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách trong phiếu mượn."));
            int soLuongCon = sach.getSachcon() == null ? 0 : sach.getSachcon();
            int soLuongTra = chiTiet.getSoluongmuon() == null ? 0 : chiTiet.getSoluongmuon();
            sach.setSachcon(soLuongCon + soLuongTra);
            sachRepository.save(sach);
        }

        PhieuTra phieuTra = new PhieuTra();
        phieuTra.setPhieuMuon(phieuMuon);
        phieuTra.setNgaylap(ngayTra.atStartOfDay());
        phieuTra.setGhichu(ghiChu);
        return phieuTraRepository.save(phieuTra);
    }

    public PhieuTra capNhat(Integer id, LocalDate ngayTra, String ghiChu) {
        if (ngayTra == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày trả.");
        }
        PhieuTra phieuTra = phieuTraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu trả."));
        phieuTra.setNgaylap(ngayTra.atStartOfDay());
        phieuTra.setGhichu(ghiChu);
        return phieuTraRepository.save(phieuTra);
    }

    @Transactional
    public void xoa(Integer id) {
        PhieuTra phieuTra = phieuTraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu trả."));

        PhieuMuon phieuMuon = phieuTra.getPhieuMuon();
        if (phieuMuon != null && phieuMuon.getChiTietPhieuMuons() != null) {
            for (ChiTietPhieuMuon chiTiet : phieuMuon.getChiTietPhieuMuons()) {
                if (chiTiet.getSach() == null || chiTiet.getSach().getMasach() == null) continue;

                Sach sach = sachRepository.findById(chiTiet.getSach().getMasach())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách trong phiếu mượn."));
                int soLuongCon = sach.getSachcon() == null ? 0 : sach.getSachcon();
                int soLuongMuon = chiTiet.getSoluongmuon() == null ? 0 : chiTiet.getSoluongmuon();
                if (soLuongCon < soLuongMuon) {
                    throw new IllegalArgumentException("Không thể xóa phiếu trả vì số sách trong kho không đủ.");
                }
                sach.setSachcon(soLuongCon - soLuongMuon);
                sachRepository.save(sach);
            }
        }

        phieuTraRepository.delete(phieuTra);
    }
}

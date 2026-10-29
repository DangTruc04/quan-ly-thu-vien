package com.example.duanthuvien.service;

import com.example.duanthuvien.entity.ChiTietPhieuMuon;
import com.example.duanthuvien.entity.PhieuMuon;
import com.example.duanthuvien.entity.Sach;
import com.example.duanthuvien.repository.PhieuMuonRepository;
import com.example.duanthuvien.repository.PhieuTraRepository;
import com.example.duanthuvien.repository.SachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuMuonService {

    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @Autowired
    private PhieuTraRepository phieuTraRepository;

    @Autowired
    private SachRepository sachRepository;

    public List<PhieuMuon> getAllPhieuMuon() {
        return phieuMuonRepository.findAll();
    }

    public long countPhieuMuon() {
        return phieuMuonRepository.count();
    }

    public Optional<PhieuMuon> findById(Integer id) {
        return phieuMuonRepository.findById(id);
    }


    @Transactional
    public PhieuMuon taoPhieuMuon(PhieuMuon phieuMuon) {
        truTonKho(phieuMuon.getChiTietPhieuMuons());
        return phieuMuonRepository.save(phieuMuon);
    }


    @Transactional
    public PhieuMuon capNhatPhieuMuon(Integer id, PhieuMuon thongTinMoi,
                                      List<ChiTietPhieuMuon> chiTietMoi) {
        PhieuMuon existing = phieuMuonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy phiếu mượn cần sửa!"));

        hoanTonKho(existing.getChiTietPhieuMuons());
        truTonKho(chiTietMoi);

        existing.setSinhVien(thongTinMoi.getSinhVien());
        existing.setNgaylap(thongTinMoi.getNgaylap());
        existing.setNgayhentra(thongTinMoi.getNgayhentra());

        existing.getChiTietPhieuMuons().clear();
        for (ChiTietPhieuMuon chiTiet : chiTietMoi) {
            chiTiet.setId(null);
            chiTiet.setPhieuMuon(existing);
            existing.getChiTietPhieuMuons().add(chiTiet);
        }

        return phieuMuonRepository.save(existing);
    }


    @Transactional
    public void xoaPhieuMuon(Integer id) {
        PhieuMuon phieuMuon = phieuMuonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy phiếu mượn cần xóa!"));

        hoanTonKho(phieuMuon.getChiTietPhieuMuons());
        phieuMuonRepository.delete(phieuMuon);
    }

    public long countPhieuTraByPhieuMuon(Integer maphieumuon) {
        return phieuTraRepository.countByPhieuMuonMaphieumuon(maphieumuon);
    }

    private void truTonKho(List<ChiTietPhieuMuon> chiTietList) {
        if (chiTietList == null) {
            return;
        }

        for (ChiTietPhieuMuon chiTiet : chiTietList) {
            if (chiTiet == null || chiTiet.getSach() == null
                    || chiTiet.getSach().getMasach() == null) {
                continue;
            }

            Sach sach = sachRepository.findById(chiTiet.getSach().getMasach())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Sách được chọn không tồn tại."));

            int soLuongCon = sach.getSachcon() == null ? 0 : sach.getSachcon();
            int soLuongMuon = chiTiet.getSoluongmuon() == null
                    ? 0 : chiTiet.getSoluongmuon();

            if (soLuongMuon > soLuongCon) {
                throw new IllegalArgumentException("Sách \"" + sach.getTensach()
                        + "\" chỉ còn " + soLuongCon + " cuốn, không đủ số lượng mượn.");
            }

            sach.setSachcon(soLuongCon - soLuongMuon);
            sachRepository.save(sach);
        }
    }

    private void hoanTonKho(List<ChiTietPhieuMuon> chiTietList) {
        if (chiTietList == null) {
            return;
        }

        for (ChiTietPhieuMuon chiTiet : chiTietList) {
            if (chiTiet == null || chiTiet.getSach() == null
                    || chiTiet.getSach().getMasach() == null) {
                continue;
            }

            sachRepository.findById(chiTiet.getSach().getMasach()).ifPresent(sach -> {
                int soLuongCon = sach.getSachcon() == null ? 0 : sach.getSachcon();
                int soLuongMuon = chiTiet.getSoluongmuon() == null
                        ? 0 : chiTiet.getSoluongmuon();
                sach.setSachcon(soLuongCon + soLuongMuon);
                sachRepository.save(sach);
            });
        }
    }
}

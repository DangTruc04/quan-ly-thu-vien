package com.example.duanthuvien;

import com.example.duanthuvien.entity.ChiTietPhieuMuon;
import com.example.duanthuvien.entity.PhieuMuon;
import com.example.duanthuvien.entity.PhieuTra;
import com.example.duanthuvien.entity.Sach;
import com.example.duanthuvien.repository.ChiTietPhieuMuonRepository;
import com.example.duanthuvien.repository.PhieuMuonRepository;
import com.example.duanthuvien.repository.PhieuTraRepository;
import com.example.duanthuvien.repository.SachRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class DuanthuvienApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuanthuvienApplication.class, args);
	}

	/**
	 * Khởi tạo số sách còn lại cho dữ liệu sách đã có trước khi thêm cột sachcon.
	 * Số còn lại = tổng số lượng - số đang mượn (các phiếu mượn chưa trả).
	 * Đồng thời bổ sung hạn trả cho các phiếu mượn cũ (ngày lập + 14 ngày).
	 */
	@Bean
	CommandLineRunner khoiTaoSachCon(SachRepository sachRepository,
									 ChiTietPhieuMuonRepository chiTietPhieuMuonRepository,
									 PhieuTraRepository phieuTraRepository,
									 PhieuMuonRepository phieuMuonRepository) {
		return args -> {
			Set<Integer> phieuDaTra = new HashSet<>();
			for (PhieuTra phieuTra : phieuTraRepository.findAll()) {
				if (phieuTra.getPhieuMuon() != null
						&& phieuTra.getPhieuMuon().getMaphieumuon() != null) {
					phieuDaTra.add(phieuTra.getPhieuMuon().getMaphieumuon());
				}
			}

			Map<Integer, Integer> soDangMuon = new HashMap<>();
			for (ChiTietPhieuMuon chiTiet : chiTietPhieuMuonRepository.findAll()) {
				if (chiTiet.getPhieuMuon() == null || chiTiet.getSach() == null
						|| chiTiet.getSach().getMasach() == null) {
					continue;
				}

				Integer maphieumuon = chiTiet.getPhieuMuon().getMaphieumuon();
				if (maphieumuon != null && phieuDaTra.contains(maphieumuon)) {
					continue;
				}

				int soLuong = chiTiet.getSoluongmuon() == null ? 0 : chiTiet.getSoluongmuon();
				soDangMuon.merge(chiTiet.getSach().getMasach(), soLuong, Integer::sum);
			}

			for (Sach sach : sachRepository.findAll()) {
				if (sach.getSachcon() != null) {
					continue;
				}

				int tongSoLuong = sach.getSoluong() == null ? 0 : sach.getSoluong();
				int dangMuon = soDangMuon.getOrDefault(sach.getMasach(), 0);
				sach.setSachcon(Math.max(0, tongSoLuong - dangMuon));
				sachRepository.save(sach);
			}

			for (PhieuMuon phieuMuon : phieuMuonRepository.findAll()) {
				if (phieuMuon.getNgayhentra() != null) {
					continue;
				}

				if (phieuMuon.getNgaylap() == null) {
					phieuMuon.setNgaylap(java.time.LocalDate.now());
				}
				phieuMuon.setNgayhentra(phieuMuon.getNgaylap()
						.plusDays(PhieuMuon.SO_NGAY_MUON_MAC_DINH));
				phieuMuonRepository.save(phieuMuon);
			}
		};
	}

}

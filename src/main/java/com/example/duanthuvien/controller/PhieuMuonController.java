package com.example.duanthuvien.controller;

import com.example.duanthuvien.entity.ChiTietPhieuMuon;
import com.example.duanthuvien.entity.PhieuMuon;
import com.example.duanthuvien.entity.SinhVien;
import com.example.duanthuvien.service.PhieuMuonService;
import com.example.duanthuvien.service.PhieuTraService;
import com.example.duanthuvien.service.SachService;
import com.example.duanthuvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/phieumuon")
public class PhieuMuonController {

    @Autowired
    private PhieuMuonService phieuMuonService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private SachService sachService;

    @Autowired
    private PhieuTraService phieuTraService;

    // 1. Hiển thị danh sách phiếu mượn
    @GetMapping
    public String danhSachPhieuMuon(Model model) {
        model.addAttribute("listPhieuMuon", phieuMuonService.getAllPhieuMuon());
        Set<Integer> phieuDaTra = phieuTraService.getAll().stream()
                .filter(pt -> pt.getPhieuMuon() != null)
                .map(pt -> pt.getPhieuMuon().getMaphieumuon())
                .collect(Collectors.toSet());
        model.addAttribute("phieuDaTra", phieuDaTra);
        return "phieumuon/index";
    }

    // 2. Mở form thêm mới phiếu mượn
    @GetMapping("/them")
    public String themPhieuMuonForm(Model model) {
        PhieuMuon phieuMuon = new PhieuMuon();
        
        phieuMuon.setNgaylap(LocalDate.now());
        phieuMuon.setNgayhentra(phieuMuon.getNgaylap().plusDays(14));

        // Khởi tạo sẵn 1 dòng chi tiết mượn trống để giao diện hiển thị sẵn
        ChiTietPhieuMuon chiTiet = new ChiTietPhieuMuon();
        chiTiet.setSoluongmuon(1);
        phieuMuon.getChiTietPhieuMuons().add(chiTiet);

        model.addAttribute("phieuMuon", phieuMuon);
        model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
        model.addAttribute("listSach", sachService.getAllSach().stream()
                .filter(sach -> sach.getSachcon() != null && sach.getSachcon() > 0)
                .toList());
        return "phieumuon/them";
    }

    // 3. Xử lý lưu phiếu mượn mới
    @PostMapping("/them")
    public String themPhieuMuon(@ModelAttribute("phieuMuon") PhieuMuon phieuMuon,
                                Model model, RedirectAttributes ra) {

        if (phieuMuon.getNgaylap() == null) {
            model.addAttribute("error", "Vui lòng chọn ngày lập.");
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/them";
        }
        phieuMuon.setNgayhentra(phieuMuon.getNgaylap().plusDays(14));

        if (phieuMuon.getSinhVien() == null || phieuMuon.getSinhVien().getMasv() == null) {
            model.addAttribute("error", "Vui lòng chọn sinh viên.");
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/them";
        }
        Optional<SinhVien> sinhVien = sinhVienService.findById(phieuMuon.getSinhVien().getMasv());
        if (sinhVien.isEmpty()) {
            model.addAttribute("error", "Sinh viên được chọn không tồn tại.");
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/them";
        }
        phieuMuon.setSinhVien(sinhVien.get());
        
        // Lọc và chuẩn hóa danh sách sách mượn từ form gửi lên
        List<ChiTietPhieuMuon> chiTietList = resolveChiTiet(phieuMuon);
        
        if (chiTietList.isEmpty()) {
            model.addAttribute("error", "Vui lòng chọn ít nhất một sách để mượn.");
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/them";
        }

        phieuMuon.setMaphieumuon(null);
        
        // Gắn quan hệ 2 chiều giữa phiếu mượn và chi tiết
        for (ChiTietPhieuMuon chiTiet : chiTietList) {
            chiTiet.setId(null);
            chiTiet.setPhieuMuon(phieuMuon);
        }
        phieuMuon.setChiTietPhieuMuons(chiTietList);

        // Lưu xuống database
        try {
            phieuMuonService.taoPhieuMuon(phieuMuon);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/them";
        }

        ra.addFlashAttribute("msg", "Đã thêm phiếu mượn thành công!");
        return "redirect:/phieumuon";
    }

    // 4. Mở form cập nhật phiếu mượn theo ID
    @GetMapping("/sua/{id}")
    public String suaPhieuMuonForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Optional<PhieuMuon> optionalPhieuMuon = phieuMuonService.findById(id);
        if (optionalPhieuMuon.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy phiếu mượn cần sửa!");
            return "redirect:/phieumuon";
        }
        
        PhieuMuon phieuMuon = optionalPhieuMuon.get();

        model.addAttribute("phieuMuon", phieuMuon);
        model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
        model.addAttribute("listSach", sachService.getAllSach());
        return "phieumuon/sua";
    }

    // 5. Xử lý lưu cập nhật phiếu mượn
    @PostMapping("/sua/{id}")
    public String capNhatPhieuMuon(@PathVariable("id") Integer id,
                                   @ModelAttribute("phieuMuon") PhieuMuon phieuMuon,
                                   Model model, RedirectAttributes ra) {
        Optional<PhieuMuon> optionalPhieuMuon = phieuMuonService.findById(id);
        if (optionalPhieuMuon.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy phiếu mượn cần sửa!");
            return "redirect:/phieumuon";
        }

        if (phieuMuon.getNgaylap() == null) {
            model.addAttribute("error", "Vui lòng chọn ngày lập.");
            phieuMuon.setMaphieumuon(id);
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/sua";
        }
        phieuMuon.setNgayhentra(phieuMuon.getNgaylap().plusDays(14));

        List<ChiTietPhieuMuon> chiTietList = resolveChiTiet(phieuMuon);
        if (chiTietList.isEmpty()) {
            model.addAttribute("error", "Vui lòng chọn ít nhất một sách để mượn.");
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/sua";
        }

        phieuMuon.setMaphieumuon(id);
        for (ChiTietPhieuMuon chiTiet : chiTietList) {
            chiTiet.setPhieuMuon(phieuMuon);
        }

        // Gọi service cập nhật
        try {
            phieuMuonService.capNhatPhieuMuon(id, phieuMuon, chiTietList);
        } catch (IllegalArgumentException ex) {
            phieuMuon.setMaphieumuon(id);
            phieuMuon.setChiTietPhieuMuons(chiTietList);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
            model.addAttribute("listSach", sachService.getAllSach());
            return "phieumuon/sua";
        }

        ra.addFlashAttribute("msg", "Đã cập nhật thành công!");
        return "redirect:/phieumuon";
    }

    // 6. Xóa phiếu mượn
    @PostMapping("/xoa/{id}")
    public String xoaPhieuMuon(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            phieuMuonService.xoaPhieuMuon(id);
            ra.addFlashAttribute("msg", "Đã xóa thành công!");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMsg", "Không thể xóa phiếu mượn này!");
        }
        return "redirect:/phieumuon";
    }

    // --- Hàm phụ trợ đơn giản hóa việc lấy danh sách chi tiết sách ---
    private List<ChiTietPhieuMuon> resolveChiTiet(PhieuMuon phieuMuon) {
        List<ChiTietPhieuMuon> result = new ArrayList<>();
        if (phieuMuon.getChiTietPhieuMuons() == null) {
            return result;
        }

        for (ChiTietPhieuMuon chiTiet : phieuMuon.getChiTietPhieuMuons()) {
            // Bỏ qua nếu dòng không chọn sách
            if (chiTiet == null || chiTiet.getSach() == null || chiTiet.getSach().getMasach() == null) {
                continue;
            }

            // Gắn thông tin sách đầy đủ từ database vào
            sachService.findById(chiTiet.getSach().getMasach()).ifPresent(chiTiet::setSach);

            // Mặc định số lượng là 1 nếu bỏ trống
            if (chiTiet.getSoluongmuon() == null || chiTiet.getSoluongmuon() < 1) {
                chiTiet.setSoluongmuon(1);
            }

            result.add(chiTiet);
        }
        return result;
    }
}

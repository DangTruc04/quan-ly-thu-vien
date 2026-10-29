package com.example.duanthuvien.controller;

import com.example.duanthuvien.entity.PhieuTra;
import com.example.duanthuvien.service.PhieuMuonService;
import com.example.duanthuvien.service.PhieuTraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/phieutra")
public class PhieuTraController {

    @Autowired
    private PhieuTraService phieuTraService;

    @Autowired
    private PhieuMuonService phieuMuonService;

    @GetMapping
    public String danhSach(Model model) {
        model.addAttribute("listPhieuTra", phieuTraService.getAll());
        return "phieutra/index";
    }

    @GetMapping("/them")
    public String formThem(@RequestParam(required = false) Integer maphieumuon, Model model) {
        model.addAttribute("phieuTra", new PhieuTra());
        model.addAttribute("ngayTra", LocalDate.now());
        model.addAttribute("selectedMaphieumuon", maphieumuon);
        napDanhSachPhieuMuonChuaTra(model);
        return "phieutra/them";
    }

    @PostMapping("/them")
    public String them(@RequestParam Integer maphieumuon,
                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayTra,
                       @RequestParam(required = false) String ghichu,
                       RedirectAttributes ra) {
        try {
            phieuTraService.taoPhieuTra(maphieumuon, ngayTra, ghichu);
            ra.addFlashAttribute("msg", "Đã tạo phiếu trả thành công.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/phieutra";
    }

    @GetMapping("/sua/{id}")
    public String formSua(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        Optional<PhieuTra> ketQua = phieuTraService.findById(id);
        if (ketQua.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy phiếu trả.");
            return "redirect:/phieutra";
        }
        model.addAttribute("phieuTra", ketQua.get());
        model.addAttribute("ngayTra", ketQua.get().getNgaylap().toLocalDate());
        return "phieutra/sua";
    }

    @PostMapping("/sua/{id}")
    public String sua(@PathVariable Integer id,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayTra,
                      @RequestParam(required = false) String ghichu,
                      RedirectAttributes ra) {
        try {
            phieuTraService.capNhat(id, ngayTra, ghichu);
            ra.addFlashAttribute("msg", "Đã cập nhật phiếu trả.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/phieutra";
    }

    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            phieuTraService.xoa(id);
            ra.addFlashAttribute("msg", "Đã xóa phiếu trả.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/phieutra";
    }

    private void napDanhSachPhieuMuonChuaTra(Model model) {
        List<com.example.duanthuvien.entity.PhieuMuon> list = phieuMuonService.getAllPhieuMuon()
                .stream()
                .filter(pm -> phieuMuonService.countPhieuTraByPhieuMuon(pm.getMaphieumuon()) == 0)
                .toList();
        model.addAttribute("listPhieuMuon", list);
    }
}

package com.example.duanthuvien.controller;

import com.example.duanthuvien.entity.SinhVien;
import com.example.duanthuvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String danhSachSinhVien(Model model) {
        model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
        return "sinhvien/index";
    }

    @GetMapping("/them")
    public String themSinhVienForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "sinhvien/them";
    }

    @PostMapping("/them")
    public String themSinhVien(@ModelAttribute("sinhVien") SinhVien sinhVien,
                               Model model, RedirectAttributes ra) {
        if (sinhVien.getMasv() == null || isBlank(String.valueOf(sinhVien.getMasv()))) {
            model.addAttribute("error", "Vui lòng nhập mã sinh viên.");
            return "sinhvien/them";
        }
        if (isBlank(sinhVien.getTensv())) {
            model.addAttribute("error", "Vui lòng nhập đầy đủ họ tên sinh viên.");
            return "sinhvien/them";
        }

        if (sinhVienService.findById(sinhVien.getMasv()).isPresent()) {
            model.addAttribute("error", "Mã sinh viên đã tồn tại.");
            return "sinhvien/them";
        }

        sinhVienService.save(sinhVien);
        ra.addFlashAttribute("msg", "Đã thêm sinh viên thành công!");
        return "redirect:/sinhvien";
    }

    @GetMapping("/sua/{id}")
    public String suaSinhVien(@PathVariable("id") Integer id, Model model) {
        Optional<SinhVien> optionalSinhVien = sinhVienService.findById(id);
        if (optionalSinhVien.isEmpty()) {
            return "redirect:/sinhvien";
        }
        model.addAttribute("sinhVien", optionalSinhVien.get());
        return "sinhvien/sua";
    }

    @PostMapping("/sua/{id}")
    public String capNhatSinhVien(@PathVariable("id") Integer id,
                                  @ModelAttribute("sinhVien") SinhVien sinhVien,
                                  Model model, RedirectAttributes ra) {
        Optional<SinhVien> optionalSinhVien = sinhVienService.findById(id);
        if (optionalSinhVien.isEmpty()) {
            ra.addFlashAttribute("msg", "Không tìm thấy sinh viên cần sửa!");
            return "redirect:/sinhvien";
        }

        if (isBlank(sinhVien.getTensv())) {
            model.addAttribute("error", "Vui lòng nhập đầy đủ họ tên sinh viên.");
            return "sinhvien/sua";
        }

        SinhVien existing = optionalSinhVien.get();
        existing.setTensv(sinhVien.getTensv().trim());

        sinhVienService.save(existing);
        ra.addFlashAttribute("msg", "Đã lưu thành công!");
        return "redirect:/sinhvien";
    }

    @PostMapping("/xoa/{id}")
    public String xoaSinhVien(@PathVariable("id") Integer id, RedirectAttributes ra) {
        sinhVienService.deleteById(id);
        ra.addFlashAttribute("msg", "Đã xóa thành công!");
        return "redirect:/sinhvien";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

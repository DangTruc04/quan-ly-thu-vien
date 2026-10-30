package com.example.demo.controller;

import com.example.demo.entity.SinhVien;
import com.example.demo.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sinh-vien")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String listSinhVien(Model model) {
        model.addAttribute("sinhViens", sinhVienService.getAllSinhVien());
        return "admin/sinh-vien-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "admin/sinh-vien-form";
    }

    @PostMapping("/save")
    public String saveSinhVien(@ModelAttribute SinhVien sinhVien) {
        sinhVienService.saveSinhVien(sinhVien);
        return "redirect:/admin/sinh-vien";
    }

    @GetMapping("/edit/{masv}")
    public String showEditForm(@PathVariable Long masv, Model model) {
        SinhVien sinhVien = sinhVienService.getSinhVienById(masv);
        model.addAttribute("sinhVien", sinhVien);
        return "admin/sinh-vien-form";
    }

    @GetMapping("/delete/{masv}")
    public String deleteSinhVien(@PathVariable Long masv) {
        sinhVienService.deleteSinhVien(masv);
        return "redirect:/admin/sinh-vien";
    }
}

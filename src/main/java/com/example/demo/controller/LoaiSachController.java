package com.example.demo.controller;

import com.example.demo.entity.LoaiSach;
import com.example.demo.service.LoaiSachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/loai-sach")
public class LoaiSachController {
    @Autowired
    private LoaiSachService loaiSachService;

    @GetMapping
    public String listLoaiSach(Model model) {
        model.addAttribute("loaiSachs", loaiSachService.getAllLoaiSach());
        return "admin/loai-sach-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("loaiSach", new LoaiSach());
        return "admin/loai-sach-form";
    }

    @PostMapping("/save")
    public String saveLoaiSach(@ModelAttribute LoaiSach loaiSach) {
        loaiSachService.saveLoaiSach(loaiSach);
        return "redirect:/admin/loai-sach";
    }

    @GetMapping("/edit/{maloai}")
    public String showEditForm(@PathVariable Long maloai, Model model) {
        LoaiSach loaiSach = loaiSachService.getLoaiSachById(maloai);
        model.addAttribute("loaiSach", loaiSach);
        return "admin/loai-sach-form";
    }

    @GetMapping("/delete/{maloai}")
    public String deleteLoaiSach(@PathVariable Long maloai) {
        loaiSachService.deleteLoaiSach(maloai);
        return "redirect:/admin/loai-sach";
    }
}

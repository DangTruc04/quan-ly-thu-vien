package com.example.demo.controller;

import com.example.demo.entity.PhieuTra;
import com.example.demo.service.PhieuTraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/phieu-tra")
public class PhieuTraController {
    @Autowired
    private PhieuTraService phieuTraService;

    @GetMapping
    public String listPhieuTra(Model model) {
        model.addAttribute("phieuTras", phieuTraService.getAllPhieuTra());
        return "admin/phieu-tra-list";
    }

    @GetMapping("/add/{maphieumuon}")
    public String showAddForm(@PathVariable Long maphieumuon, Model model) {
        model.addAttribute("maphieumuon", maphieumuon);
        return "admin/phieu-tra-form";
    }

    @PostMapping("/save")
    public String savePhieuTra(@RequestParam Long maphieumuon) {
        phieuTraService.createPhieuTra(maphieumuon);
        return "redirect:/admin/phieu-tra";
    }

    @GetMapping("/view/{maphieutra}")
    public String viewPhieuTra(@PathVariable Long maphieutra, Model model) {
        PhieuTra phieuTra = phieuTraService.getPhieuTraById(maphieutra);
        model.addAttribute("phieuTra", phieuTra);
        return "admin/phieu-tra-detail";
    }
}

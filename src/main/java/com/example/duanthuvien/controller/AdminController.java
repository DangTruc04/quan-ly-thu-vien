package com.example.duanthuvien.controller;

import com.example.duanthuvien.entity.Sach;
import com.example.duanthuvien.service.PhieuMuonService;
import com.example.duanthuvien.service.SachService;
import com.example.duanthuvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private SachService sachService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private PhieuMuonService phieuMuonService;

    @GetMapping({"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("tongSoSach", sachService.countSach());
        model.addAttribute("tongSinhVien", sinhVienService.countSinhVien());
        model.addAttribute("tongPhieuMuon", phieuMuonService.countPhieuMuon());
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("listSinhVien", sinhVienService.getAllSinhVien());
        return "users";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("sach", new Sach());
        return "ecommerce-add-product";
    }
}
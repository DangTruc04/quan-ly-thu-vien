package com.example.demo.controller;

import com.example.demo.entity.ChiTietPhieuMuon;
import com.example.demo.entity.PhieuMuon;
import com.example.demo.entity.Sach;
import com.example.demo.entity.SinhVien;
import com.example.demo.service.PhieuMuonService;
import com.example.demo.service.SachService;
import com.example.demo.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/phieu-muon")
public class PhieuMuonController {
    @Autowired
    private PhieuMuonService phieuMuonService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private SachService sachService;

    @GetMapping
    public String listPhieuMuon(Model model) {
        model.addAttribute("phieuMuons", phieuMuonService.getAllPhieuMuon());
        return "admin/phieu-muon-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("sinhViens", sinhVienService.getAllSinhVien());
        model.addAttribute("sachs", sachService.getAllSach());
        return "admin/phieu-muon-form";
    }

    @PostMapping("/save")
    public String savePhieuMuon(@RequestParam Long masv, 
                                @RequestParam(required = false) List<Long> sachIds,
                                @RequestParam(required = false) List<Integer> soLuongs) {
        SinhVien sinhVien = sinhVienService.getSinhVienById(masv);
        if (sachIds != null && !sachIds.isEmpty()) {
            phieuMuonService.createPhieuMuon(sinhVien, sachIds, soLuongs);
        }
        return "redirect:/admin/phieu-muon";
    }

    @GetMapping("/view/{maphieumuon}")
    public String viewPhieuMuon(@PathVariable Long maphieumuon, Model model) {
        PhieuMuon phieuMuon = phieuMuonService.getPhieuMuonById(maphieumuon);
        model.addAttribute("phieuMuon", phieuMuon);
        return "admin/phieu-muon-detail";
    }
}

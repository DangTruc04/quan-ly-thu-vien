package com.example.demo.controller;

import com.example.demo.entity.LoaiSach;
import com.example.demo.entity.Sach;
import com.example.demo.entity.SinhVien;
import com.example.demo.service.LoaiSachService;
import com.example.demo.service.SachService;
import com.example.demo.service.SinhVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private SachService sachService;

    @Autowired
    private LoaiSachService loaiSachService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("sachs", sachService.getAllSach());
        model.addAttribute("loaiSachs", loaiSachService.getAllLoaiSach());
        model.addAttribute("sinhViens", sinhVienService.getAllSinhVien());
        model.addAttribute("user", session.getAttribute("user"));
        return "index";
    }

    @GetMapping("/sach/loai/{maloai}")
    public String sachByLoai(@PathVariable Long maloai, Model model, HttpSession session) {
        LoaiSach loaiSach = loaiSachService.getLoaiSachById(maloai);
        model.addAttribute("sachs", sachService.getSachByLoai(maloai));
        model.addAttribute("loaiSachs", loaiSachService.getAllLoaiSach());
        model.addAttribute("sinhViens", sinhVienService.getAllSinhVien());
        model.addAttribute("selectedLoai", loaiSach);
        model.addAttribute("user", session.getAttribute("user"));
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        model.addAttribute("sachs", sachService.searchSach(keyword));
        model.addAttribute("loaiSachs", loaiSachService.getAllLoaiSach());
        model.addAttribute("sinhViens", sinhVienService.getAllSinhVien());
        model.addAttribute("keyword", keyword);
        model.addAttribute("user", session.getAttribute("user"));
        return "index";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("totalSach", sachService.getAllSach().size());
        model.addAttribute("totalLoaiSach", loaiSachService.getAllLoaiSach().size());
        model.addAttribute("totalSinhVien", sinhVienService.getAllSinhVien().size());
        return "admin/dashboard";
    }
}

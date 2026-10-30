package com.example.demo.controller;

import com.example.demo.entity.LoaiSach;
import com.example.demo.entity.Sach;
import com.example.demo.service.LoaiSachService;
import com.example.demo.service.SachService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/sach")
public class SachController {
    @Autowired
    private SachService sachService;

    @Autowired
    private LoaiSachService loaiSachService;

    @GetMapping
    public String listSach(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("sachs", sachService.getAllSach());
        model.addAttribute("loaiSachs", loaiSachService.getAllLoaiSach());
        return "admin/sach-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("sach", new Sach());
        model.addAttribute("loaiSachs", loaiSachService.getAllLoaiSach());
        return "admin/sach-form";
    }

    @PostMapping("/save")
    public String saveSach(@ModelAttribute Sach sach, 
                            @RequestParam Long maloai,
                            @RequestParam(required = false) MultipartFile imageFile,
                            HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        LoaiSach loaiSach = loaiSachService.getLoaiSachById(maloai);
        sach.setLoaiSach(loaiSach);
        
        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();
                // For demo, we'll just use the filename. In production, upload to cloud storage
                sach.setImageUrl("/uploads/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        sachService.saveSach(sach);
        return "redirect:/admin/sach";
    }

    @GetMapping("/edit/{masach}")
    public String showEditForm(@PathVariable Long masach, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        Sach sach = sachService.getSachById(masach);
        model.addAttribute("sach", sach);
        model.addAttribute("loaiSachs", loaiSachService.getAllLoaiSach());
        return "admin/sach-form";
    }

    @GetMapping("/delete/{masach}")
    public String deleteSach(@PathVariable Long masach, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        sachService.deleteSach(masach);
        return "redirect:/admin/sach";
    }
}

package com.example.demo.controller;

import com.example.demo.entity.SinhVien;
import com.example.demo.service.SinhVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        HttpSession session, 
                        Model model) {
        // Admin account (hardcoded for demo)
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("user", username);
            session.setAttribute("role", "ADMIN");
            return "redirect:/admin/sach";
        }
        
        // Check in database for user accounts
        var sinhVien = sinhVienService.getAllSinhVien().stream()
            .filter(sv -> sv.getUsername() != null && sv.getUsername().equals(username))
            .findFirst();
        
        if (sinhVien.isPresent() && sinhVien.get().getPassword() != null && sinhVien.get().getPassword().equals(password)) {
            session.setAttribute("user", sinhVien.get().getUsername());
            session.setAttribute("userId", sinhVien.get().getMasv());
            session.setAttribute("fullName", sinhVien.get().getTensv());
            session.setAttribute("role", "USER");
            return "redirect:/";
        } else {
            model.addAttribute("error", true);
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute SinhVien sinhVien, Model model) {
        try {
            sinhVienService.saveSinhVien(sinhVien);
            model.addAttribute("success", true);
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", true);
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

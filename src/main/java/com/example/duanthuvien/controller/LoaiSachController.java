package com.example.duanthuvien.controller;

import com.example.duanthuvien.entity.LoaiSach;
import com.example.duanthuvien.service.LoaiSachService;
import com.example.duanthuvien.service.SachService;
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
@RequestMapping("/loaisach")
public class LoaiSachController {

    @Autowired
    private LoaiSachService loaiSachService;

    @Autowired
    private SachService sachService;

    @GetMapping
    public String danhSachLoaiSach(Model model) {
        model.addAttribute("listLoaiSach", loaiSachService.getAllLoaiSach());
        return "loaisach/index";
    }

    @GetMapping("/them")
    public String themLoaiSachForm(Model model) {
        LoaiSach loaiSach = new LoaiSach();
        loaiSach.setTrangthai(1);
        model.addAttribute("loaiSach", loaiSach);
        return "loaisach/them";
    }

    @PostMapping("/them")
    public String themLoaiSach(@ModelAttribute("loaiSach") LoaiSach loaiSach,
                               Model model, RedirectAttributes ra) {
        if (isBlank(loaiSach.getTenloai())) {
            model.addAttribute("error", "Vui lòng nhập tên loại sách.");
            return "loaisach/them";
        }

        String tenLoai = loaiSach.getTenloai().trim();
        if (loaiSachService.existsByTenloai(tenLoai)) {
            model.addAttribute("error", "Loại sách \"" + tenLoai + "\" đã tồn tại.");
            return "loaisach/them";
        }

        loaiSach.setMaloai(null);
        loaiSach.setTenloai(tenLoai);
        if (loaiSach.getTrangthai() == null) {
            loaiSach.setTrangthai(1);
        }

        loaiSachService.save(loaiSach);
        ra.addFlashAttribute("msg", "Đã thêm loại sách \"" + tenLoai + "\" thành công!");
        return "redirect:/loaisach";
    }

    @GetMapping("/sua/{id}")
    public String suaLoaiSach(@PathVariable("id") Integer id, Model model,
                              RedirectAttributes ra) {
        Optional<LoaiSach> optionalLoaiSach = loaiSachService.findById(id);
        if (optionalLoaiSach.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy loại sách cần sửa!");
            return "redirect:/loaisach";
        }
        model.addAttribute("loaiSach", optionalLoaiSach.get());
        return "loaisach/sua";
    }

    @PostMapping("/sua/{id}")
    public String capNhatLoaiSach(@PathVariable("id") Integer id,
                                  @ModelAttribute("loaiSach") LoaiSach loaiSach,
                                  Model model, RedirectAttributes ra) {
        Optional<LoaiSach> optionalLoaiSach = loaiSachService.findById(id);
        if (optionalLoaiSach.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy loại sách cần sửa!");
            return "redirect:/loaisach";
        }

        loaiSach.setMaloai(id);

        if (isBlank(loaiSach.getTenloai())) {
            model.addAttribute("error", "Vui lòng nhập tên loại sách.");
            return "loaisach/sua";
        }

        String tenLoai = loaiSach.getTenloai().trim();
        if (loaiSachService.existsByTenloaiAndMaloaiNot(tenLoai, id)) {
            model.addAttribute("error", "Loại sách \"" + tenLoai + "\" đã tồn tại.");
            return "loaisach/sua";
        }

        LoaiSach existing = optionalLoaiSach.get();
        existing.setTenloai(tenLoai);
        if (loaiSach.getTrangthai() != null) {
            existing.setTrangthai(loaiSach.getTrangthai());
        }

        loaiSachService.save(existing);
        ra.addFlashAttribute("msg", "Đã lưu thành công!");
        return "redirect:/loaisach";
    }

    @PostMapping("/xoa/{id}")
    public String xoaLoaiSach(@PathVariable("id") Integer id, RedirectAttributes ra) {
        Optional<LoaiSach> optionalLoaiSach = loaiSachService.findById(id);
        if (optionalLoaiSach.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy loại sách cần xóa!");
            return "redirect:/loaisach";
        }

        long soSach = sachService.countByLoai(id);
        if (soSach > 0) {
            ra.addFlashAttribute("errorMsg",
                    "Không thể xóa: loại sách này đang có " + soSach + " đầu sách.");
            return "redirect:/loaisach";
        }

        loaiSachService.deleteById(id);
        ra.addFlashAttribute("msg", "Đã xóa thành công!");
        return "redirect:/loaisach";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

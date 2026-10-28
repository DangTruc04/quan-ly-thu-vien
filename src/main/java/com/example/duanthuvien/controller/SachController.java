package com.example.duanthuvien.controller;

import com.example.duanthuvien.entity.LoaiSach;
import com.example.duanthuvien.entity.Sach;
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
@RequestMapping("/sach")
public class SachController {

    @Autowired
    private SachService sachService;

    @Autowired
    private LoaiSachService loaiSachService;

    @GetMapping
    public String danhSachSach(Model model) {
        model.addAttribute("listSach", sachService.getAllSach());
        return "sach/index";
    }

    @GetMapping("/them")
    public String themSachForm(Model model) {
        Sach sach = new Sach();
        sach.setTaiban(1);
        sach.setSoluong(0);
        sach.setTrangthai(1);
        model.addAttribute("sach", sach);
        model.addAttribute("listLoaiSach", loaiSachService.getAllLoaiSach());
        return "sach/them";
    }

    @PostMapping("/them")
    public String themSach(@ModelAttribute("sach") Sach sach,
                           Model model, RedirectAttributes ra) {
        model.addAttribute("listLoaiSach", loaiSachService.getAllLoaiSach());
        LoaiSach loaiSach = resolveLoaiSach(sach);

        if (!isValid(sach, loaiSach, model)) {
            return "sach/them";
        }

        String tenSach = sach.getTensach().trim();
        if (sachService.existsByTensach(tenSach)) {
            model.addAttribute("error", "Sách \"" + tenSach + "\" đã tồn tại.");
            return "sach/them";
        }

        sach.setMasach(null);
        sach.setTensach(tenSach);
        sach.setLoaiSach(loaiSach);
        if (sach.getTaiban() == null) {
            sach.setTaiban(1);
        }
        if (sach.getSoluong() == null) {
            sach.setSoluong(0);
        }
        if (sach.getTrangthai() == null) {
            sach.setTrangthai(1);
        }

        sachService.save(sach);
        ra.addFlashAttribute("msg", "Đã thêm sách \"" + tenSach + "\" thành công!");
        return "redirect:/sach";
    }

    @GetMapping("/sua/{id}")
    public String suaSach(@PathVariable("id") Integer id, Model model,
                          RedirectAttributes ra) {
        Optional<Sach> optionalSach = sachService.findById(id);
        if (optionalSach.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy sách cần sửa!");
            return "redirect:/sach";
        }
        model.addAttribute("sach", optionalSach.get());
        model.addAttribute("listLoaiSach", loaiSachService.getAllLoaiSach());
        return "sach/sua";
    }

    @PostMapping("/sua/{id}")
    public String capNhatSach(@PathVariable("id") Integer id,
                              @ModelAttribute("sach") Sach sach,
                              Model model, RedirectAttributes ra) {
        Optional<Sach> optionalSach = sachService.findById(id);
        if (optionalSach.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy sách cần sửa!");
            return "redirect:/sach";
        }

        sach.setMasach(id);
        model.addAttribute("listLoaiSach", loaiSachService.getAllLoaiSach());
        LoaiSach loaiSach = resolveLoaiSach(sach);

        if (!isValid(sach, loaiSach, model)) {
            return "sach/sua";
        }

        String tenSach = sach.getTensach().trim();
        if (sachService.existsByTensachAndMasachNot(tenSach, id)) {
            model.addAttribute("error", "Sách \"" + tenSach + "\" đã tồn tại.");
            return "sach/sua";
        }

        Sach existing = optionalSach.get();
        existing.setTensach(tenSach);
        existing.setLoaiSach(loaiSach);
        if (sach.getTaiban() != null) {
            existing.setTaiban(sach.getTaiban());
        }
        if (sach.getSoluong() != null) {
            existing.setSoluong(sach.getSoluong());
        }
        if (sach.getTrangthai() != null) {
            existing.setTrangthai(sach.getTrangthai());
        }

        sachService.save(existing);
        ra.addFlashAttribute("msg", "Đã lưu thành công!");
        return "redirect:/sach";
    }

    @PostMapping("/xoa/{id}")
    public String xoaSach(@PathVariable("id") Integer id, RedirectAttributes ra) {
        Optional<Sach> optionalSach = sachService.findById(id);
        if (optionalSach.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Không tìm thấy sách cần xóa!");
            return "redirect:/sach";
        }

        long soPhieuMuon = sachService.countChiTietBySach(id);
        if (soPhieuMuon > 0) {
            ra.addFlashAttribute("errorMsg",
                    "Không thể xóa: sách này đang có trong " + soPhieuMuon + " phiếu mượn.");
            return "redirect:/sach";
        }

        sachService.deleteById(id);
        ra.addFlashAttribute("msg", "Đã xóa thành công!");
        return "redirect:/sach";
    }

    private LoaiSach resolveLoaiSach(Sach sach) {
        if (sach.getLoaiSach() == null || sach.getLoaiSach().getMaloai() == null) {
            return null;
        }
        return loaiSachService.findById(sach.getLoaiSach().getMaloai()).orElse(null);
    }

    private boolean isValid(Sach sach, LoaiSach loaiSach, Model model) {
        if (isBlank(sach.getTensach())) {
            model.addAttribute("error", "Vui lòng nhập tên sách.");
            return false;
        }
        if (loaiSach == null) {
            model.addAttribute("error", "Vui lòng chọn loại sách.");
            return false;
        }
        if (sach.getTaiban() != null && sach.getTaiban() < 1) {
            model.addAttribute("error", "Tái bản phải từ 1 trở lên.");
            return false;
        }
        if (sach.getSoluong() != null && sach.getSoluong() < 0) {
            model.addAttribute("error", "Số lượng không được âm.");
            return false;
        }
        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

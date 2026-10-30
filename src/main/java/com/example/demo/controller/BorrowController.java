package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Borrow;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.BorrowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @GetMapping("/borrow/{bookId}")
    public String borrowForm(@PathVariable Long bookId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        model.addAttribute("user", user);
        return "borrow";
    }

    @PostMapping("/borrow")
    public String borrow(@RequestParam Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Book book = bookService.getBookById(bookId);
        borrowService.createBorrow(user, book);
        return "redirect:/my-borrows";
    }

    @GetMapping("/my-borrows")
    public String myBorrows(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Borrow> borrows = borrowService.getUserBorrows(user.getId());
        model.addAttribute("borrows", borrows);
        model.addAttribute("user", user);
        return "my-borrows";
    }

    @GetMapping("/return/{borrowId}")
    public String returnBook(@PathVariable Long borrowId) {
        borrowService.returnBook(borrowId);
        return "redirect:/my-borrows";
    }

    @GetMapping("/admin/borrows")
    public String allBorrows(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Borrow> borrows = borrowService.getAllBorrows();
        model.addAttribute("borrows", borrows);
        model.addAttribute("user", user);
        return "admin-borrows";
    }
}

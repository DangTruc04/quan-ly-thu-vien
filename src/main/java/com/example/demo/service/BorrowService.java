package com.example.demo.service;

import com.example.demo.entity.Borrow;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    public Borrow createBorrow(User user, Book book) {
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setDueDate(LocalDate.now().plusDays(14));
        borrow.setStatus("BORROWED");
        
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            return borrowRepository.save(borrow);
        }
        return null;
    }

    public Borrow returnBook(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId).orElse(null);
        if (borrow != null && "BORROWED".equals(borrow.getStatus())) {
            borrow.setReturnDate(LocalDate.now());
            borrow.setStatus("RETURNED");
            
            Book book = borrow.getBook();
            book.setQuantity(book.getQuantity() + 1);
            
            return borrowRepository.save(borrow);
        }
        return null;
    }

    public List<Borrow> getUserBorrows(Long userId) {
        return borrowRepository.findByUserId(userId);
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Borrow getBorrowById(Long id) {
        return borrowRepository.findById(id).orElse(null);
    }
}

package com.example.demo.config;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final LoaiSachRepository loaiSachRepository;
    private final SinhVienRepository sinhVienRepository;
    private final SachRepository sachRepository;
    private final AuthorRepository authorRepository;

    public DataInitializer(LoaiSachRepository loaiSachRepository,
                          SinhVienRepository sinhVienRepository,
                          SachRepository sachRepository,
                          AuthorRepository authorRepository) {
        this.loaiSachRepository = loaiSachRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.sachRepository = sachRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (loaiSachRepository.count() == 0) {
            // Tạo Loại Sách
            LoaiSach loai1 = new LoaiSach("Văn Học");
            LoaiSach loai2 = new LoaiSach("Khoa Học");
            LoaiSach loai3 = new LoaiSach("Kinh Tế");
            LoaiSach loai4 = new LoaiSach("Lịch Sử");
            LoaiSach loai5 = new LoaiSach("Tâm Lý");
            LoaiSach loai6 = new LoaiSach("Kỹ Năng");

            loaiSachRepository.save(loai1);
            loaiSachRepository.save(loai2);
            loaiSachRepository.save(loai3);
            loaiSachRepository.save(loai4);
            loaiSachRepository.save(loai5);
            loaiSachRepository.save(loai6);

            // Tạo Tác Giả
            Author author1 = new Author("Nguyễn Nhật Ánh");
            Author author2 = new Author("Tô Hoài");
            Author author3 = new Author("Nam Cao");
            Author author4 = new Author("Stephen Hawking");
            Author author5 = new Author("Adam Smith");
            Author author6 = new Author("Dale Carnegie");
            
            authorRepository.save(author1);
            authorRepository.save(author2);
            authorRepository.save(author3);
            authorRepository.save(author4);
            authorRepository.save(author5);
            authorRepository.save(author6);

            // Tạo Sinh Viên
            SinhVien sv1 = new SinhVien("Nguyễn Văn An");
            sv1.setUsername("nguyenvan");
            sv1.setPassword("user123");
            sv1.setEmail("an.nguyen@example.com");
            sv1.setSdt("0123456789");
            sv1.setLop("CNTT-K15");

            SinhVien sv2 = new SinhVien("Trần Thị Bình");
            sv2.setUsername("tranthi");
            sv2.setPassword("user123");
            sv2.setEmail("binh.tran@example.com");
            sv2.setSdt("0987654321");
            sv2.setLop("KT-K15");

            SinhVien sv3 = new SinhVien("Lê Văn Cường");
            sv3.setUsername("levan");
            sv3.setPassword("user123");
            sv3.setEmail("cuong.le@example.com");
            sv3.setSdt("0112233445");
            sv3.setLop("QTKD-K15");

            sinhVienRepository.save(sv1);
            sinhVienRepository.save(sv2);
            sinhVienRepository.save(sv3);

            // Tạo Sách
            Sach sach1 = new Sach("Mắt Biếc", 5);
            sach1.setLoaiSach(loai1);
            sach1.setAuthor(author1);
            sach1.setTaiban(2);
            sach1.setGia(150000.0);
            sach1.setImageUrl("https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=600&fit=crop");
            sach1.setTrangthai("Available");

            Sach sach2 = new Sach("Điều Giày Thủy Thử", 3);
            sach2.setLoaiSach(loai1);
            sach2.setAuthor(author2);
            sach2.setTaiban(1);
            sach2.setGia(120000.0);
            sach2.setImageUrl("https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400&h=600&fit=crop");
            sach2.setTrangthai("Available");

            Sach sach3 = new Sach("Chí Phèo", 4);
            sach3.setLoaiSach(loai1);
            sach3.setAuthor(author3);
            sach3.setTaiban(3);
            sach3.setGia(95000.0);
            sach3.setImageUrl("https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=400&h=600&fit=crop");
            sach3.setTrangthai("Available");

            Sach sach4 = new Sach("Thời Gian Ngắn Gọn", 2);
            sach4.setLoaiSach(loai2);
            sach4.setAuthor(author4);
            sach4.setTaiban(1);
            sach4.setGia(250000.0);
            sach4.setImageUrl("https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400&h=600&fit=crop");
            sach4.setTrangthai("Available");

            Sach sach5 = new Sach("The Wealth of Nations", 3);
            sach5.setLoaiSach(loai3);
            sach5.setAuthor(author5);
            sach5.setTaiban(2);
            sach5.setGia(350000.0);
            sach5.setImageUrl("https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=400&h=600&fit=crop");
            sach5.setTrangthai("Available");

            Sach sach6 = new Sach("Lịch Sử Việt Nam", 4);
            sach6.setLoaiSach(loai4);
            sach6.setAuthor(author1);
            sach6.setTaiban(2);
            sach6.setGia(180000.0);
            sach6.setImageUrl("https://images.unsplash.com/photo-1461360370896-922624d12aa1?w=400&h=600&fit=crop");
            sach6.setTrangthai("Available");

            Sach sach7 = new Sach("Tâm Lý Học", 3);
            sach7.setLoaiSach(loai5);
            sach7.setAuthor(author4);
            sach7.setTaiban(1);
            sach7.setGia(220000.0);
            sach7.setImageUrl("https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=400&h=600&fit=crop");
            sach7.setTrangthai("Available");

            Sach sach8 = new Sach("Đắc Nhân Tâm", 6);
            sach8.setLoaiSach(loai6);
            sach8.setAuthor(author6);
            sach8.setTaiban(4);
            sach8.setGia(175000.0);
            sach8.setImageUrl("https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=400&h=600&fit=crop");
            sach8.setTrangthai("Available");

            Sach sach9 = new Sach("Tâm Lý Học Đám Đông", 4);
            sach9.setLoaiSach(loai5);
            sach9.setAuthor(author4);
            sach9.setTaiban(2);
            sach9.setGia(210000.0);
            sach9.setImageUrl("https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=400&h=600&fit=crop");
            sach9.setTrangthai("Available");

            Sach sach10 = new Sach("Kinh Tế Vi Mô", 5);
            sach10.setLoaiSach(loai3);
            sach10.setAuthor(author5);
            sach10.setTaiban(3);
            sach10.setGia(195000.0);
            sach10.setImageUrl("https://images.unsplash.com/photo-1554224155-6726b3ff858f?w=400&h=600&fit=crop");
            sach10.setTrangthai("Available");

            sachRepository.save(sach1);
            sachRepository.save(sach2);
            sachRepository.save(sach3);
            sachRepository.save(sach4);
            sachRepository.save(sach5);
            sachRepository.save(sach6);
            sachRepository.save(sach7);
            sachRepository.save(sach8);
            sachRepository.save(sach9);
            sachRepository.save(sach10);

            System.out.println("Sample data initialized successfully!");
            System.out.println("Created 6 book types, 3 students, and 10 books");
        }
    }
}

//package com.example.studySpring.Book.Service;
//
//import com.example.studySpring.Book.BookRepository.BookRepository;
//import com.example.studySpring.Book.Models.Book;
//import com.example.studySpring.Book.Models.ResponseObject;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.elasticsearch.ResourceNotFoundException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
////import java.awt.print.Pageable;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BookService {
//
//    private final BookRepository bookRepository;
//
//    public BookService(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    public List<Book> findAll(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size,Sort.by("title"));
//        return bookRepository.findAll(pageable).getContent();
//    }
//
//
//    public Optional<Book> getBookById(int id) {
//        return bookRepository.findById(id);
//    }
//
//    public Book save(Book book){
//        return bookRepository.save(book);
//    }
//
//    // update by id
//    public String updateTitleById(int id, String newTitle) {
//        Optional<Book> optionalBookbook = bookRepository.findById(id);
//        if (optionalBookbook.isPresent()){
//            Book book = optionalBookbook.get();
//            book.setTitle(newTitle);
//            bookRepository.save(book);
//            return "Success";
//        }else {
//            return "Fail";
//        }
//    }
//    //find by name of book
//    public List<Book> findByBookTitle(int page, int size, String bookTitle){
//        Pageable pageable = PageRequest.of(page,size,Sort.by("price").ascending());
//        return bookRepository.findByTitleContaining(bookTitle, pageable).getContent();
//    }
//    //find by id of book
//    public Optional<Book> findById(int id){
//        return bookRepository.findById(id);
//    }
//    public List<Book> findByLanguage(String language){
//        return bookRepository.findBookWithLanguage(language);
//    }
//
//
//    //check exist by ID
//    public boolean existById(int id){
//        return bookRepository.existsById(id);
//    }
//    //delete by id
//    public void deleteById(int id){
//        bookRepository.deleteById(id);
//    }
//
//}

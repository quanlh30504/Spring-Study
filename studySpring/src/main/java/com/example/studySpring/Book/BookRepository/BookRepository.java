//package com.example.studySpring.Book.BookRepository;
//
//import com.example.studySpring.Book.Models.Book;
//import org.hibernate.Internal;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//@Repository
//public interface BookRepository extends JpaRepository<Book, Integer> {
//    Page<Book> findByTitleContaining(String bookTitle,Pageable pageable);
//    @Query("Select b from Book b where b.language = :language")
//    List<Book> findBookWithLanguage(@Param("language") String language);
//
////    Page<Book> findAll(Pageable pageable);
//
//
//
//}

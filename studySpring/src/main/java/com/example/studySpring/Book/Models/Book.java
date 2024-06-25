//package com.example.studySpring.Book.Models;
//import jakarta.persistence.*;
//
//import java.util.Optional;
//
//@Entity
//@Table(name = "book")
//public class Book {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @Column(name = "title",nullable = false,length = 300,unique = true)
//    private String title;
//
//    @Column(name = "price")
//    private Double price;
//    @Column(name = "language", length = 20)
//    private String language;
//
//
//    public Book(){}
//
//    public Book(Book newBook){
//        this.id = newBook.id;
//        this.title = newBook.title;
//        this.language = newBook.language;
//        this.price = newBook.price;
//    }
//    public Book(int id, String title, String language, Double price) {
//        this.id = id;
//        this.title = title;
//        this.language = language;
//        this.price = price;
//    }
//
//    @Override
//    public String toString() {
//        return "Book{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", price=" + price +
//                ", language='" + language + '\'' +
//                '}';
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//}

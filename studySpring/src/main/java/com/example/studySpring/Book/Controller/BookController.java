//package com.example.studySpring.Book.Controller;
//
//import com.example.studySpring.Book.Models.Book;
//import com.example.studySpring.Book.Models.BookDTO;
//import com.example.studySpring.Book.Models.ResponseObject;
//import com.example.studySpring.Book.Service.BookService;
//import com.example.studySpring.ExceptionHandling.ExceptionNotFoundId;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/v1/books")
//public class BookController {
//
//    public BookService bookService;
//    public BookController(BookService bookService){
//        this.bookService = bookService;
//    }
//
//    @GetMapping("/findAll")
//    public ResponseEntity<ResponseObject> findALl(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size)
//    {
//        List<Book> books = bookService.findAll(page, size);
//        List<BookDTO> bookDTOS = books.stream()
//                .map((Book book) -> new BookDTO(book.getId(), book.getTitle(), book.getPrice()))
//                .toList();
//        if(books.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject("failed", "Not have Book", null)
//            );
//        }
//        return ResponseEntity.ok().body(
//                new ResponseObject("success", "Book found", bookDTOS)
//        );
//    }
//
//    //find book by id
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseObject> getBookById(@PathVariable(name = "id") int id){
//        Optional<Book> book = bookService.getBookById(id);
//        if(book.isPresent()) {
//            return (
//                    ResponseEntity.status(HttpStatus.OK).body(
//                            new ResponseObject("Success", "Success to get book", List.of(book.get()))
//                    )
//            );
//        }else{
//            throw new ExceptionNotFoundId(String.format("Not found book id: %d",id));
//        }
//    }
//    @GetMapping("/search")
//    public ResponseEntity<ResponseObject> getBookByTitle(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam String title
//    )
//    {
//        List<Book> books = bookService.findByBookTitle(page,size,title);
//        if(books.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject("Failed","Not found book",null)
//            );
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("Success","Found book successfully",books)
//        );
//    }
//
//    // find book by language
//    @GetMapping("/language")
//    public ResponseEntity<ResponseObject> findBookByLanguage(@RequestParam String language){
//        List<Book> books = bookService.findByLanguage(language);
//        if(books.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject("Failed","Not found book!", null)
//            );
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("Success","Found book successfully",books)
//        );
//    }
//
//    //insert book with POST method
//    //Postman: raw, JSON
//    @PostMapping("")
//    ResponseEntity<ResponseObject>insertBook(@RequestBody Book book){
//        // 2 books must not have the same name
//        List<Book> foundBooks = bookService.findByBookTitle(0,100, book.getTitle().trim());
//        if(!foundBooks.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//                    new ResponseObject("failed","Book is in store",null)
//            );
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//               new ResponseObject("ok","Insert Book successfully",List.of(bookService.save(book)))
//        );
//    }
//
//    //update, upsert = update if found, otherwise insert
//    @Transactional
//    @PutMapping("/{id}")
//    ResponseEntity<ResponseObject> updateBook(@RequestBody Book newBook, @PathVariable(name = "id") int id){
//        Book updateProduct = (Book) bookService.findById(id)   // vì sao ở đây đã dùng map với orElseGet rồi mà vẫn phải ép kiểu
//                .map(book -> {
//                    book.setTitle(newBook.getTitle());
//                    book.setLanguage(newBook.getLanguage());
//                    return bookService.save(book);
//                }).orElseGet(() -> {
//                    newBook.setId(id);
//                    return bookService.save(newBook);
//                });
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("ok","update successfully",List.of(updateProduct))
//        );
//    }
//    //update title
//    @Transactional
//    @PutMapping("/updateBookTitle")
//    public ResponseEntity<String> updateTitleBook(
//            @RequestParam int id,
//            @RequestParam String newTitle
//    )
//    {
//        String status = bookService.updateTitleById(id,newTitle);
//        if(status.equals("Success")){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    "Update Successfully"
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(
//                    "Update Failed"
//            );
//        }
//    }
//
//    @Transactional
//    @DeleteMapping("/{id}")
//    ResponseEntity<ResponseObject> deleteBook(@PathVariable(name = "id") int id){
//        boolean exist = bookService.existById(id);
//        if(exist){
//            bookService.deleteById(id);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok","Delete Successfully",null)
//            );
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                new ResponseObject("failed","can't find this book to delete",null)
//        );
//    }
//
//
//
//
//}

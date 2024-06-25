package com.example.studySpring.Controllers;

import com.example.studySpring.Models.Product;
import com.example.studySpring.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);

    }
//    @ExceptionHandler(ExceptionNotFoundId.class)
//    public ResponseEntity<?> ExceptionHandel(Exception exception){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//    }


    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody Product newProduct) {
        return ResponseEntity.ok(productService.save(newProduct));
    }
}
package com.example.studySpring.Controllers;

import com.example.studySpring.Models.Category;
import com.example.studySpring.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCateagoryById(@PathVariable int id){
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}

package com.example.studySpring.Service;


import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Models.Category;
import com.example.studySpring.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryById(int id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.CATEGORY_ID_NOT_EXISTED));
        return category;
    }
}

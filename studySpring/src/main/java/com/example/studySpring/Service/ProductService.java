package com.example.studySpring.Service;

import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Models.Product;
import com.example.studySpring.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;


    public Product findById(int id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_EXISTED));
        return product;
    }

    public String save(Product newProduct){
        repository.save(newProduct);
        return "Successfully";
    }

}

package com.example.studySpring.Models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
//    @Column(name = "created_at")
    private LocalDateTime created_at;

//    @Column(name = "updated _at")
    private LocalDateTime updated_at;

    @PrePersist // Tự động gọi phương thức khi thêm mới đối tượng vào DB
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate // Tự động gọi phương thức khi cập nhật ối tượng đã có trong DB
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }
}

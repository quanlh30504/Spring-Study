package com.example.studySpring.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User{
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname", length = 100)
    private String fullName;
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
    @Column(name = "address", length = 200)
    private String address;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String username;
    @Column(name = "password", length = 100)
    private String password;

//    private Set<String> roles;

//    @OneToMany
//    @JoinColumn(name = "role_id", referencedColumnName = "id")
//    private Role role;
}

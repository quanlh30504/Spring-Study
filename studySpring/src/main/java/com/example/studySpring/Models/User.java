package com.example.studySpring.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
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

    

//    @OneToMany
//    @JoinColumn(name = "role_id", referencedColumnName = "id")
//    private Role role;
}

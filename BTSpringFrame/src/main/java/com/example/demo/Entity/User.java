package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "username", length = 50)
    private String username;
    
    @Column(name = "password", length = 50)
    private String password;
    
    @Column(name = "fullname", columnDefinition = "NVARCHAR(255)")
    private String fullname;
    
    @Column(name = "email", columnDefinition = "NVARCHAR(255)")
    private String email;

    @Column(name = "roleid")
    private int roleid;
}
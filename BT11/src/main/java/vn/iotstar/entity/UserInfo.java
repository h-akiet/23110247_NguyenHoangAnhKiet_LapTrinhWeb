package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "UserInfo")
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;
}

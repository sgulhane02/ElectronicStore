package com.BikkadIT.electronic.store.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name= "users")
public class User {
    @Id
    @Column(name = "id")
    private String UserId;

    @Column(name = "username")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name="user_password",length = 10)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name= "user_image")
    private String image;
}

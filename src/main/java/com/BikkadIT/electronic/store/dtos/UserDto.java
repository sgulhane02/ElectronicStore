package com.BikkadIT.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String UserId;

    @Size(min=3, max=50, message = "Invalid Name !!")
    private String name;
    //@Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\r\n",message="Invalid user email ")
    private String email;
    @NotBlank(message= "Password is required !!")
    private String password;
    @Size(min=4, max=6,message="Invalid gender !!")
    private String gender;
    @NotBlank(message="Write something in about section")
    private String about;
    private String image;
}

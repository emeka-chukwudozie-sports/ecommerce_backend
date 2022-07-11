package com.saha.e_commerce.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpDto {
    private String firstName;
    private  String lastName;
    private String email;
    private String password;
}

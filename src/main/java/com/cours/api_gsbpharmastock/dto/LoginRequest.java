package com.cours.api_gsbpharmastock.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String mail;
    private String password;

}

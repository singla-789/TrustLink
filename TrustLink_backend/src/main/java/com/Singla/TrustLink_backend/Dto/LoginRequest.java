package com.Singla.TrustLink_backend.Dto;


import lombok.Data;

import java.util.Set;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

package com.Singla.TrustLink_backend.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlMappingDto{
    private Long id;
    private String originalUrl;
    private String ShortUrl;
    private int clickCount;
    private LocalDateTime createdDate;
    private String username;

}

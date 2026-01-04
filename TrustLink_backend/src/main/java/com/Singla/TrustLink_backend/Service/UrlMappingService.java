package com.Singla.TrustLink_backend.Service;

import com.Singla.TrustLink_backend.Dto.UrlMappingDto;
import com.Singla.TrustLink_backend.Repositary.UrlMappingRepositary;
import com.Singla.TrustLink_backend.modles.UrlMapping;
import com.Singla.TrustLink_backend.modles.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepositary urlMappingRepositary;

    public UrlMappingDto createShortUrl(String originalUrl, User user) {
        String shortUrl = getShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());

        UrlMapping savedUrlMapping = urlMappingRepositary.save(urlMapping);

        return convertToDto(savedUrlMapping);

    }

    public UrlMappingDto convertToDto(UrlMapping urlMapping){
        UrlMappingDto newDto = new UrlMappingDto();
        newDto.setId(urlMapping.getId());
        newDto.setShortUrl(urlMapping.getShortUrl());
        newDto.setOriginalUrl(urlMapping.getOriginalUrl());
        newDto.setCreatedDate(urlMapping.getCreatedDate());
        newDto.setUsername(urlMapping.getUser().getUsername());
        newDto.setClickCount(urlMapping.getClickCount());

        return newDto;
    }

    public String getShortUrl(){
        StringBuilder shortUrl = new StringBuilder(8);
        Random random = new Random();
        String c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        for (int i =0;i<8;i++){
            shortUrl.append(c.charAt(random.nextInt(c.length())));
        }
        return shortUrl.toString();
    }

    public List<UrlMappingDto> getMyUrls(User user){
        List<UrlMapping> myUrls = urlMappingRepositary.findByUser(user);
        List<UrlMappingDto> myUrlDtos = myUrls.stream().map(e -> convertToDto(e)).toList();
        return myUrlDtos;
    }
}

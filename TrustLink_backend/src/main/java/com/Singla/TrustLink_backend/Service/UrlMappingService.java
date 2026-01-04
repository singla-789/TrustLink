package com.Singla.TrustLink_backend.Service;

import com.Singla.TrustLink_backend.Dto.ClickEventDto;
import com.Singla.TrustLink_backend.Dto.UrlMappingDto;
import com.Singla.TrustLink_backend.Repositary.ClickEventRepositary;
import com.Singla.TrustLink_backend.Repositary.UrlMappingRepositary;
import com.Singla.TrustLink_backend.modles.ClickEvent;
import com.Singla.TrustLink_backend.modles.UrlMapping;
import com.Singla.TrustLink_backend.modles.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepositary urlMappingRepositary;
    private ClickEventRepositary clickEventRepositary;

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

    public List<ClickEventDto> getClickEventByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepositary.findByShortUrl(shortUrl);
        if(urlMapping != null){
           return clickEventRepositary.findByUrlMappingAndClickDateBetween(urlMapping,start,end).stream()
                   .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(),Collectors.counting()))
                   .entrySet().stream()
                   .map(entry -> {
                       ClickEventDto clickEventDto = new ClickEventDto();
                       clickEventDto.setClickDate(entry.getKey());
                       clickEventDto.setCount(entry.getValue());
                       return clickEventDto;
                   }).collect(Collectors.toList());

        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepositary.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepositary.findByUrlMappingInAndClickDateBetween(urlMappings,start.atStartOfDay(),end.plusDays(1).atStartOfDay());

        return clickEvents.stream().collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(),Collectors.counting()));

    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepositary.findByShortUrl(shortUrl);
        if(urlMapping != null){
            urlMapping.setClickCount(urlMapping.getClickCount()+1);
            urlMappingRepositary.save(urlMapping);

//            click evernts
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            clickEventRepositary.save(clickEvent);
        }
        return urlMapping;
    }
}

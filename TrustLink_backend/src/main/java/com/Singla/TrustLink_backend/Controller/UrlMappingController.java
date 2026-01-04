package com.Singla.TrustLink_backend.Controller;

import com.Singla.TrustLink_backend.Dto.ClickEventDto;
import com.Singla.TrustLink_backend.Dto.UrlMappingDto;
import com.Singla.TrustLink_backend.Service.UrlMappingService;
import com.Singla.TrustLink_backend.Service.UserService;
import com.Singla.TrustLink_backend.modles.ClickEvent;
import com.Singla.TrustLink_backend.modles.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

    private UrlMappingService urlMappingService;
    private UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDto> shortenUrl(@RequestBody Map<String,String> request, Principal principal){

        String originalUrl = request.get("originalUrl");
        User user = userService.findByUserName(principal.getName());
        UrlMappingDto urlMappingDto = urlMappingService.createShortUrl(originalUrl,user);
        return ResponseEntity.ok(urlMappingDto);
    }

    @GetMapping("/myUrls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDto>> fetchMyUrls(Principal principal){
        User user = userService.findByUserName(principal.getName());
        List<UrlMappingDto> myUrls = urlMappingService.getMyUrls(user);
        return ResponseEntity.ok(myUrls);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDto>> getUrlAnalytics(@PathVariable String shortUrl,
                                                           @RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate,formatter);
        LocalDateTime end = LocalDateTime.parse(endDate,formatter);
        List<ClickEventDto> events = urlMappingService.getClickEventByDate(shortUrl,start,end);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate,Long>> getTotalClicksByDate(Principal principal,
                                                           @RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate){
        User user = userService.findByUserName(principal.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate,formatter);
        Map<LocalDate,Long> clickEventDtos = urlMappingService.getTotalClicksByUserAndDate(user,start,end);
        return  ResponseEntity.ok(clickEventDtos);
    }
}

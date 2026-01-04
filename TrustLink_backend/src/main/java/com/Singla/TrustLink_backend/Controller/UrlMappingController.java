package com.Singla.TrustLink_backend.Controller;

import com.Singla.TrustLink_backend.Dto.UrlMappingDto;
import com.Singla.TrustLink_backend.Service.UrlMappingService;
import com.Singla.TrustLink_backend.Service.UserService;
import com.Singla.TrustLink_backend.modles.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
}

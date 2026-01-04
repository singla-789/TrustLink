package com.Singla.TrustLink_backend.Repositary;

import com.Singla.TrustLink_backend.modles.UrlMapping;
import com.Singla.TrustLink_backend.modles.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlMappingRepositary extends JpaRepository<UrlMapping,Long> {
    UrlMapping findByShortUrl(String shortUrl);
    List<UrlMapping> findByUser(User user);
}

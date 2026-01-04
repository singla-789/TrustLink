package com.Singla.TrustLink_backend.Repositary;

import com.Singla.TrustLink_backend.modles.ClickEvent;
import com.Singla.TrustLink_backend.modles.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepositary extends JpaRepository<ClickEvent,Long> {
    List<ClickEvent> findByUrlMappingAndClickDateBetween(UrlMapping urlMapping, LocalDateTime start,LocalDateTime end);
    List<ClickEvent> findByUrlMappingInAndClickDateBetween(List<UrlMapping> mappings,LocalDateTime start,LocalDateTime end);
}

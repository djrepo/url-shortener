package com.shorturl.model;

import jakarta.persistence.*;

@Entity
@Table(name="URL_ENTITY")
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORIGINAL_URL", unique=true, columnDefinition="TEXT")
    private String originalUrl;

    public UrlEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @Override
    public String toString() {
        return "UrlEntity [id=" + id + ", longUrl=" + originalUrl + "]";
    }
}

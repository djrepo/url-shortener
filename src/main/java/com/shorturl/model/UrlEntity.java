package com.shorturl.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

@Entity
@Table(name="URL_ENTITY", indexes = {
        @Index(columnList = "ORIGINAL_URL_HASH", name = "hash_idx")})
@Cacheable
public class UrlEntity implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORIGINAL_URL", unique=true, columnDefinition="TEXT")
    private String originalUrl;

    @Column(name = "ORIGINAL_URL_HASH", unique=true)
    private String originalUrlHash;

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

    public String getOriginalUrlHash() {
        return originalUrlHash;
    }

    public void setOriginalUrlHash(String originalUrlHash) {
        this.originalUrlHash = originalUrlHash;
    }

    @Override
    public String toString() {
        return "UrlEntity [id=" + id + ", longUrl=" + originalUrl + "]";
    }
}

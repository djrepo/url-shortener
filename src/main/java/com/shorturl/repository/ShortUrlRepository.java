package com.shorturl.repository;

import com.shorturl.model.UrlEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Repository class for managing URL entities.
 */
@ApplicationScoped
public class ShortUrlRepository {
    @PersistenceContext
    private EntityManager em;

    public Long store(UrlEntity urlEntity) {
        em.persist(urlEntity);
        return urlEntity.getId();
    }

    public UrlEntity findById(long id) {
        return em.createQuery("SELECT u FROM UrlEntity u WHERE u.id = :id", UrlEntity.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public UrlEntity findByHashAndOriginalUrl(UrlEntity urlEntity) {
        return em.createQuery("SELECT u FROM UrlEntity u WHERE u.originalUrlHash = :hash and u.originalUrl = : originalUrl", UrlEntity.class)
                .setParameter("hash", urlEntity.getOriginalUrlHash())
                .setParameter("originalUrl", urlEntity.getOriginalUrl())
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}

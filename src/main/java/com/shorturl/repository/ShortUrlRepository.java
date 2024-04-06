package com.shorturl.repository;

import com.shorturl.model.UrlEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Repository class for managing URL entities.
 */
@ApplicationScoped
public class ShortUrlRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public long store(UrlEntity urlEntity) {
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

}

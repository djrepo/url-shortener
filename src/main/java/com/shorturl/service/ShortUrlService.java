package com.shorturl.service;

import com.shorturl.model.UrlEntity;
import com.shorturl.repository.IShortUrlRepository;
import com.shorturl.repository.ShortUrlRepository;
import com.shorturl.utils.Base62EncoderDecoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.logging.Logger;

import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLOutput;
import java.util.Set;

@ApplicationScoped
public class ShortUrlService implements IShortUrlService {

    private static final Logger LOG = Logger.getLogger(ShortUrlService.class.toString());

    @Inject
    private IShortUrlRepository shortUrlRepository;

    @Inject
    @ConfigProperty(name = "domain")
    private String domain;

    @Override
    public String getOriginalUrl(String shortUrl) {
        long id = Base62EncoderDecoder.decodeLong(shortUrl);
        LOG.info(shortUrl+" translated "+id);
        UrlEntity urlEntity = shortUrlRepository.findById(id);
        if (urlEntity!=null){
            return urlEntity.getOriginalUrl();
        }
        return null;
    }

    @Transactional
    @Override
    public String createShortUrl(String originalUrl){
        UrlEntity newUrlEntity = createUrlEntity(originalUrl);
        UrlEntity storedUrlEntity = storeOrRetrieve(newUrlEntity);
        String shortURL = Base62EncoderDecoder.encode(storedUrlEntity.getId());
        LOG.info("Entity with id "+storedUrlEntity.getId()+ " will be mapped to postfix  "+shortURL);
        return domain+"/"+shortURL;
    }

    private UrlEntity storeOrRetrieve(UrlEntity entity) {
        UrlEntity alreadyStoredUrlEntity = shortUrlRepository.findByHashAndOriginalUrl(entity);
        LOG.info("Looking for entity with hash "+entity.getOriginalUrlHash());
        if (alreadyStoredUrlEntity!=null) {
            LOG.info("Found entity " + alreadyStoredUrlEntity.getId()+" "+alreadyStoredUrlEntity.getOriginalUrl());
            return alreadyStoredUrlEntity;
        }
        Long id = shortUrlRepository.store(entity);
        LOG.info("Not Found entity with id " + entity.getId()+" therefore persisting and getting id "+entity.getId());
        entity.setId(id);
        return entity;
    }

    private UrlEntity createUrlEntity(String originalUrl) {
        String hash = DigestUtils.md5Hex(originalUrl).toUpperCase();
        UrlEntity entity = new UrlEntity();
        entity.setOriginalUrl(originalUrl);
        entity.setOriginalUrlHash(hash);
        return entity;
    }

}

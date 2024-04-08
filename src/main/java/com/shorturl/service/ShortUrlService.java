package com.shorturl.service;

import com.shorturl.model.UrlEntity;
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
public class ShortUrlService {

    private static final Logger LOG = Logger.getLogger(ShortUrlService.class.toString());

    @Inject
    private ShortUrlRepository shortUrlRepository;

    @Inject
    @ConfigProperty(name = "domain")
    private String domain;

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
/*
    public static void main(String[] args){
        String hash = DigestUtils.md5Hex("https://www.google.com/maps/@48.6944381,21.2647604,3a,75y,359.27h,90t/data=!3m7!1e1!3m5!1sh9jmZE_WUo-IrFtCkPjKyQ!2e0!6shttps:%2F%2Fstreetviewpixels-pa.googleapis.com%2Fv1%2Fthumbnail%3Fpanoid%3Dh9jmZE_WUo-IrFtCkPjKyQ%26cb_client%3Dmaps_sv.tactile.gps%26w%3D203%26h%3D100%26yaw%3D359.27277%26pitch%3D0%26thumbfov%3D100!7i13312!8i6656?entry=ttu").toUpperCase();
        System.out.println(hash);
    }
*/

}

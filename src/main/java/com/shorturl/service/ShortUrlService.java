package com.shorturl.service;

import com.shorturl.model.UrlEntity;
import com.shorturl.repository.ShortUrlRepository;
import com.shorturl.utils.Base62EncoderDecoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ShortUrlService {

    @Inject
    private ShortUrlRepository shortUrlRepository;

    public String createShortUrl(String originalUrl){
        String domain = System.getProperty("domain");
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        long id = shortUrlRepository.store(urlEntity);
        String shortURL = Base62EncoderDecoder.encode(id);
        return domain+shortURL;
    }

    public String getOriginalUrl(String shortUrl) {
        long id = Base62EncoderDecoder.decodeLong(shortUrl);
        UrlEntity urlEntity = shortUrlRepository.findById(id);
        return urlEntity.getOriginalUrl();
    }
}

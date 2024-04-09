package com.shorturl.service;

import com.shorturl.model.UrlEntity;
import com.shorturl.utils.Base62EncoderDecoder;
import jakarta.transaction.Transactional;

public interface IShortUrlService {
    public String getOriginalUrl(String shortUrl);

    public String createShortUrl(String originalUrl);

}

package com.shorturl.repository;

import com.shorturl.model.UrlEntity;

public interface IShortUrlRepository {

    public Long store(UrlEntity urlEntity);

    public UrlEntity findById(long id);

    public UrlEntity findByHashAndOriginalUrl(UrlEntity urlEntity);

}

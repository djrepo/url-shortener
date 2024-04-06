package com.shorturl.controller;

import com.shorturl.model.LongUrlWrapper;
import com.shorturl.model.UrlEntity;
import com.shorturl.repository.ShortUrlRepository;
import com.shorturl.service.ShortUrlService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.logging.Logger;

/**
 * JAX-RS resource class for handling URL shortening operations.
 */
@Path("/url")
public class ShortUrlResource {
    private static final Logger LOG = Logger.getLogger(ShortUrlResource.class.toString());
    @Inject
    private ShortUrlService shortUrlService;

    /**
     * Convert a new URL to short url
     *
     * @param longUrlWrapper the url which needs to be shorten
     * @return short url representing originalUrl
     */
    @POST
    @Path("/shortify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String shortify(LongUrlWrapper longUrlWrapper) {
        LOG.info("shortify "+longUrlWrapper.getLongUrl());
        System.out.println(longUrlWrapper.getLongUrl());
        String shortURL = shortUrlService.createShortUrl(longUrlWrapper.getLongUrl());
        return shortURL;
    }



}

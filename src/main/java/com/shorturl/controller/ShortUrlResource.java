package com.shorturl.controller;

import com.shorturl.model.LongUrlWrapper;
import com.shorturl.model.UrlEntity;
import com.shorturl.repository.ShortUrlRepository;
import com.shorturl.service.ShortUrlService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.util.logging.Logger;

/**
 * JAX-RS resource class for handling URL shortening operations.
 */
@Path("/")
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

    /**
     * Redirects the client to the original URL associated with the provided short URL.
     * If the short URL is not found, returns a NOT FOUND response.
     *
     * @param shortUrl The short URL to be resolved to its original URL.
     * @return A response object containing either a redirection or a NOT FOUND status.
     */
    @GET
    @Path("/{shortUrl}")
    public Response redirectOriginalURL(@PathParam("shortUrl") String shortUrl) {
        LOG.info("redirect for "+shortUrl);
        String originalUrl = shortUrlService.getOriginalUrl(shortUrl);
        if (originalUrl != null) {
            // If the original URL is found, redirect the client to it
            LOG.info("Original url found for short url "+shortUrl+" original url "+originalUrl );
            return Response.temporaryRedirect(UriBuilder.fromUri(originalUrl).build()).build();
        } else {
            // If the short URL is not found, return a NOT FOUND response
            LOG.info("Original url not found for short url "+shortUrl);
            return Response.status(Response.Status.NOT_FOUND).entity("Short URL not found").build();
        }
    }



}

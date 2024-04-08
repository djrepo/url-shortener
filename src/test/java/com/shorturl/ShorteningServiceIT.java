package com.shorturl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shorturl.config.MyApplication;
import com.shorturl.controller.ShortUrlResource;
import com.shorturl.model.UrlEntity;
import com.shorturl.repository.ShortUrlRepository;
import com.shorturl.service.ShortUrlService;
import com.shorturl.utils.Base62EncoderDecoder;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Run integration tests with Arquillian to be able to test CDI beans
 */
@ExtendWith(ArquillianExtension.class)
@CleanupUsingScript("clean-database.sql")
@UsingDataSet("init-database.xml")
public class ShorteningServiceIT {

    @Deployment
    public static WebArchive createDeploymentPackage() {
        return ShrinkWrap.create(WebArchive.class, "ROOT.war")
                .addPackage(UrlEntity.class.getPackage())
                .addClass(ShortUrlService.class)
                .addClass(ShortUrlResource.class)
                .addClass(MyApplication.class)
                .addClass(ShortUrlRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/microprofile-config.properties");
    }

    @Inject
    private ShortUrlService service;

    @Test
    public void testService() {
        String result = service.createShortUrl("fdsa");
        System.out.println(result);
        int lastIndexOf = result.lastIndexOf("/");
        String shortUrlPostfix = result.substring(lastIndexOf);
        assertEquals("1", shortUrlPostfix);

        result = service.createShortUrl("fdsa");
        lastIndexOf = result.lastIndexOf("/");
        shortUrlPostfix = result.substring(lastIndexOf);
        assertEquals("1", shortUrlPostfix);
    }
}

package com.shorturl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shorturl.config.JaxRsActivator;
import com.shorturl.controller.ShortUrlResource;
import com.shorturl.model.UrlEntity;
import com.shorturl.repository.ShortUrlRepository;
import com.shorturl.service.ShortUrlService;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
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
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(UrlEntity.class.getPackage())
                .addClasses(ShortUrlService.class,ShortUrlResource.class,ShortUrlRepository.class,JaxRsActivator.class)
                .addAsResource("META-INF/persistence-h2.xml")
                //.addAsWebInfResource("arquillian-ds.xml")
                .addAsResource("META-INF/microprofile-config.properties")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private ShortUrlService service;

    @Test
    public void testService() {
        String result = service.createShortUrl("fdsa");
        System.out.println(result);
        int lastIndexOf = result.lastIndexOf("/");
        String shortUrlPostfix = result.substring(lastIndexOf);
        assertEquals("2", shortUrlPostfix);

        result = service.createShortUrl("fdsa");
        lastIndexOf = result.lastIndexOf("/");
        shortUrlPostfix = result.substring(lastIndexOf);
        assertEquals("2", shortUrlPostfix);
    }
}

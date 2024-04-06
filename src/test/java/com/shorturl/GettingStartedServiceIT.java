package com.shorturl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shorturl.service.ShortUrlService;
import com.shorturl.utils.Base62EncoderDecoder;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Run integration tests with Arquillian to be able to test CDI beans
 */
@ExtendWith(ArquillianExtension.class)
public class  GettingStartedServiceIT {

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "ROOT.war")
                .addClass(ShortUrlService.class);
    }

    @Inject
    private ShortUrlService service;

   // @Test
    public void testService() {
        String result = service.createShortUrl("fdsa");
        //assertEquals("Hello 'World'.", result);

        result = service.createShortUrl("fdsa");
        //assertEquals("Hello 'Monde'.", result);
    }
}

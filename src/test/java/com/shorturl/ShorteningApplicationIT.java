package com.shorturl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Run integration tests against the server and the deployed application.
 */
@RunAsClient
@ExtendWith(ArquillianExtension.class)
public class ShorteningApplicationIT {

    @Test
    public void testHelloEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target(URI.create("http://localhost:8080"))
                    .path("/api/0")
                    .request()
                    .get();
            assertEquals(307, response.getStatus());

        }
    }
}

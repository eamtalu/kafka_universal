package uk.co.kafka.producer.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)   // This will scan the running port and inject the port into @localserver port
class ProducerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    //https://spring.io/guides/gs/testing-web/  -- Follow this page
    @Test
    public void ProducerStreamLinkShouldWork()
    {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/producer/stream", String.class)).contains("true");
    }




}
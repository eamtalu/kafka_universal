package uk.co.kafka.producer.kafkaproducer.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import uk.co.kafka.producer.kafkaproducer.model.Logkeeper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)   // This will scan the running port and inject the port into @localserver port
@AutoConfigureMockMvc
class ProducerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;



    @Autowired
    private TestRestTemplate restTemplate;
    //https://spring.io/guides/gs/testing-web/  -- Follow this page
    @Test
    public void ProducerStreamLinkShouldWork()
    {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/producer/stream", String.class)).contains("true");
    }

    @Test
    public void shouldReturnGreeting(){
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/producer/greeting?name=masud", String.class)).contains("masud");
    }

    @Test
    public void shouldReturnTrueUponSuccessfulPost() throws Exception {

        //Create a ObjectMapper class. ObjectMapper class is the main actor of jackson library responsible for reading and writing the JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //Create a data object
        Logkeeper logkeeper = new Logkeeper(Long.valueOf(1),"testkey1", "amin talukder");
        //Convert it into json using ObjectMapper
        String logString = objectMapper.writeValueAsString(logkeeper);



        this.mvc.perform(MockMvcRequestBuilders
                .post("http://localhost:" + port + "/producer/logstream")
                .content(logString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());



    }





}
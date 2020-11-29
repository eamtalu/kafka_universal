package uk.co.kafka.producer.kafkaproducer.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;


@SpringBootTest
class ProducerConfigTest {

    @Autowired
    RoutingKafkaTemplate routingtemplate;

    @Autowired
    private ProducerFactory<Object,Object> producerFactoryString;


    //Send a message to TestTopic String serializer
    @Test
    public void sendStringMessagetoTestTopic(){
      //Object opf =  pf.getConfigurationProperties();
      routingtemplate.send("testtopic", "testkey","testmessage");
    }

    //Send a message to ipcam - byteserializer
    @Test
    public void sendStringMessagetoIpCam(){
        //Object opf =  pf.getConfigurationProperties();

        Object obj = producerFactoryString.getConfigurationProperties();
        System.out.println("test is" + obj);

        String text = "Hello";
        byte[] byteArray = text.getBytes();
        routingtemplate.send("ipcam", "test","testmessage".getBytes());
    }


}
package uk.co.kafka.producer.kafkaproducer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.kafka.producer.kafkaproducer.service.KafkaAdministration;

import java.util.Set;

@SpringBootTest
class KafkaAdministrationTest {


    //Take the service instance - service intances are always singleton, so just autowire it
    @Autowired
    KafkaAdministration ka;

//    @Autowired
//    ProducerFactory<Object, Object> pf;


    //This Test will create a topic, using the service of
    @Test
    public void shouldReturnBooleanAftertopicCreated(){
        Boolean isCreated = ka.CreateTopic("testtopic");
        System.out.println(isCreated);
    }

    //List all the kafka topics
    @Test
    public void shouldListAllTheTopic(){

        Set<String> topicHolder = null;
        topicHolder = ka.listAllTopics();
        topicHolder.forEach(ele -> System.out.println(ele));
    }

    //Delete Kafka Topic
    @Test
    public void shouldDeleteATopic(){
        Boolean delStatus = ka.deleteTopic("testTopic");
        System.out.println(delStatus);
    }
//
//    @Test
//    public void checkTheProducerFactory(){
//
//        Object test = pf.getConfigurationProperties();
//        System.out.println(test);
//
//    }

}
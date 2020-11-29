package uk.co.kafka.producer.kafkaproducer.config;


import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
@EnableKafka
public class ProducerConfig {

    //This config class returns lots of beans to the spring ios container which are used by the different services later on.
    //In this class, you will define several methods, specially related to kafka templates that will return beans.


    //Declare kafka variables
    @Value(value = "${spring.kafka.producer.bootstrap-servers}" )
    private String kafkaServer;

    // ###################################################################################################
    //      KAFKA TEMPLATE
    // ###################################################################################################
    //I want this MAP object to be staying in spring context (IOC), thats why I used @Bean annotation
    // This map object holds the key,value of producer config

    //    Create ProducerFactory for String, String
    @Bean
    public ProducerFactory<Object, Object> producerFactoryString(){
        //Create a MAP Objects and insert producerconfig data
        Map<String, Object> pcs = new HashMap<>();
        pcs.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        pcs.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        pcs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(pcs);
    }

    //Create ProducerFactory for String,Byte
//    @Bean
//    public ProducerFactory<String, byte[]> producerFactoryByte(){
//        Map<String, Object> pcb = new HashMap<>();
//        pcb.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//        pcb.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        pcb.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(pcb);
//
//    }


//    @Bean
//    public KafkaTemplate<Object,Object> kafkaTemplateString(){
//        return new KafkaTemplate<>(producerFactoryString());
//    }

//    @Bean
//    public KafkaTemplate<String,byte[]> kafkaTemplateByte(){
//        return new KafkaTemplate<>(producerFactoryByte());
//    }


    // ###################################################################################################
    //      ROUTING KAFKA TEMPLATE
    // ###################################################################################################

    // step 1: Create producer factory bean
       //Producer Factory are already created above, so no need. Make sure that there is only one defaultproducerfactory is created, not more than that

    // step 2: create routingTemplate now
    @Bean
    public RoutingKafkaTemplate routingTemplate(GenericApplicationContext context, ProducerFactory<Object,Object> pf) {

        //clone the pf which is created above, make sure there is only one default serializer is created
        // the pf, in this method parameter, will automatically be injected by spring IOC from the defaultkafkaproducer.
        Map<String, Object> configs = new HashMap<>(pf.getConfigurationProperties());
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        ProducerFactory<Object, Object> bytesPF = new DefaultKafkaProducerFactory<>(configs);
        context.registerBean(ProducerFactory.class, "bytesPF", bytesPF);  //where "bytespf" is the key and bytesPF is the value

        Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
        map.put(Pattern.compile("ipcam"), bytesPF);
        map.put(Pattern.compile("testtopic"), pf); // Default PF with String Serializer
        return new RoutingKafkaTemplate(map);

    }




}



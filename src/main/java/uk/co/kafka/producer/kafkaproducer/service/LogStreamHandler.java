package uk.co.kafka.producer.kafkaproducer.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.stereotype.Service;
import uk.co.kafka.producer.kafkaproducer.model.Logkeeper;
import uk.co.kafka.producer.kafkaproducer.repository.LogRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class LogStreamHandler {

    @Autowired
    LogRepository logRepository;

    @Autowired
    RoutingKafkaTemplate routingKafkaTemplate;


    //Save the LogKeeper record
    public boolean saveLogKeeperRecord(LogKeeperBDO logKeeperBDO){

        System.out.println("The Id is:" + logKeeperBDO.getId());
        System.out.println("The key is:" + logKeeperBDO.getLogkey());
        System.out.println("The value is:" + logKeeperBDO.getValue());

        Long BDOID = logKeeperBDO.getId();
        String BDOKey = logKeeperBDO.getLogkey();
        String BDOValue = logKeeperBDO.getValue();

        //Do the processing with the data in BDO
        //*******
        //After processing convert this BDO into DAO object with the help of Transformer.


        //Now you need a transformer to convert this BDO into DAO object. Make a transformer that is a static member of this class
        Logkeeper logkeeper = new LogKeeperRecordTransformer().logKeeperRecordTransformer(BDOID, BDOKey, BDOValue);
        //Save the DAO object now
        logRepository.save(logkeeper);

        //Also Send message to kafka
        routingKafkaTemplate.send("testtopic", String.valueOf(logKeeperBDO.getId()),logKeeperBDO.getValue());
        //Note: you can make a class or a Map object to construct a dictionary like things. Then you can use objectmapper to convert it to json. Then you can send it to routing kafkatemplate
        //Also note: the key is derived from the value. Like key can be "a" where the name are like amintalukder, ankit singhal and so on. So consumer will have this mapping known beforehand so that consumer can extract it accordingly.
        // Key is very important. It is used for selecting partition as well as stream processing.
        // ** Example with MAP object
        Map<String,String> kafkaValue = new HashMap<String, String>();
        kafkaValue.put("firstname", "amin");
        kafkaValue.put("lastname", "talukder");
        ObjectMapper kafkaJson = new ObjectMapper();
        try {
            String jsonKafkaValue = kafkaJson.writeValueAsString(kafkaValue);
            routingKafkaTemplate.send("testtopic", String.valueOf(logKeeperBDO.getId()), jsonKafkaValue);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        System.out.println(logRepository.findAll());
        return true;

    }

    //Make this BDO class as a static member of this class, since this is part of this LogStreamHandler
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LogKeeperBDO{
        Long id;
        String logkey;
        String value;
    }


    //Transformer Class that converts BDO into DAO
    public static class LogKeeperRecordTransformer {
        //One Transformer Method Only
        public Logkeeper logKeeperRecordTransformer(Long id, String key, String value){

            return Logkeeper.builder()
                    .id(id)
                    .logkey(key)
                    .value(value)
                    .build();
        }
    }


}

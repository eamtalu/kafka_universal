package uk.co.kafka.producer.kafkaproducer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.kafka.producer.kafkaproducer.service.GreetingResourceRepresentation;
import uk.co.kafka.producer.kafkaproducer.service.LogStreamHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping (path = "producer")
public class ProducerController {

    // this instance properties will be created during instantiation and will remain in the spring container cache
    // This can be used for counting the number of request
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    LogStreamHandler logStreamHandler;


    // ######################################################################################
        // Sample REST Resource Controller to say hi
    // ######################################################################################

    // Two ways to represent resource
    // In this method, representing resources by class - GreetingResourceRepresentation
    @GetMapping("/greeting")
    @ResponseBody
    public GreetingResourceRepresentation greetingController(@RequestParam(value = "name" , defaultValue = "amin") String name){
        String template = "Hellow %s !";
        return new GreetingResourceRepresentation(counter.incrementAndGet(), String.format(template,name));
    }

    //This will return a MAP as json object, java jackson library will automatically conver json into pojo and pojo(if map as key/value) into json.
    // representing resources inside the method as MAP object
    @GetMapping("/greetingmap")
    public Map<Integer,String> greetingControllerMapTest(){
        Map<Integer,String> maptest = new HashMap<>();
        maptest.put(1,"test1");
        maptest.put(2,"test2");
        return maptest;
    }


    //Write a REST Endpoint that will invoke the producer service to consume video stream
    @GetMapping("/stream")
    public @ResponseBody String StreamInvoke(){
        //Hook with streamer

        //collect the stream

        //publish it in the queue.

        return "true";
    }

    //Define an End Point that will receive the logstream and write into the queue



    //Define an End Point that will receive batch file stream and write it into the queue

    //Define an End point that will receive text input and write it into the queue


    //

    // Write a post method here that will receive the post message and send it to kafka broker
    // This DTO should contact with services not repository directly.
    @PostMapping("/logstream")
    @ResponseBody
    public String newLogRecord(@RequestBody LogStreamHandler.LogKeeperBDO logKeeperBDO){    //RequestBody that contains DTO will automatically be converted into BDO
        Boolean isSuccess = false;

        //pass it to service bdo - then service should call to save it into database
        isSuccess = logStreamHandler.saveLogKeeperRecord(logKeeperBDO);

        String response = "This is success" + isSuccess;
        return response;

    }

}

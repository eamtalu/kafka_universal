package uk.co.kafka.producer.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (path = "producer")
public class ProducerController {


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


}

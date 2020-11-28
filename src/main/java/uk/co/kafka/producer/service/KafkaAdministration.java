package uk.co.kafka.producer.service;

import lombok.val;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaAdministration {

    @Autowired
    private AdminClient admin;  //The adminclient object will be retrieved from spring container where the bean was created by config class


    //Create a CreateTopic Service
    public boolean CreateTopic(String topic_name){

        String topicKey = topic_name;
        //Get the KafkaAdmin Object
        //The object is already autowired abobe.

        //Create the topic
        //1. First create the collection, use collection.singleton since createTopics take collection as argument
        //2. Then use adminclient to create the topic
        Collection<NewTopic> newTopics = Collections.singleton(new NewTopic(topic_name, 1, (short) 1 ));
        val topicStatus = admin.createTopics(newTopics).values();
        //Return True or False

        for(String key : topicStatus.keySet()) {
//            System.out.println(topicStatus.get(key));
            topicKey = key;
        }


        if( topicKey.equals(topic_name) )
            return true;
        else
            return false;
    }

    //List topics

    public Set<String> listAllTopics(){

        Set<String> topicList = null;

        try {
            topicList = admin.listTopics().names().get();
//            topicList.forEach(ele -> System.out.println(ele));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return topicList;

    }


    //Delete Topics
    public boolean deleteTopic(String topic_name){

        val delStatus = admin.deleteTopics(Collections.singleton(topic_name)).values();
        for (String key: delStatus.keySet()){
            System.out.println(key);
        }

        return true;

    }


    //Check if a particular topic exist or not

}

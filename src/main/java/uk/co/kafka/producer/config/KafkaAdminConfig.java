package uk.co.kafka.producer.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableConfigurationProperties
//@ConfigurationProperties
public class KafkaAdminConfig {


    @Value(value = "${spring.kafka.producer.bootstrap-servers}" )
    private String kafka_server;



    // Get a Bean(Object) of kafkaadmin to create topic, manage container and so on
    // When using springboot, the kafkaadmin bean is automatically registered so you only need the NewTopic Beans below.
    @Bean
    public KafkaAdmin admin(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,kafka_server);
        return new KafkaAdmin(configs);
    }


    @Bean
    public AdminClient CustomizedKafkaAdmin(){
        return AdminClient.create(admin().getConfigurationProperties());
    }


    //Or you can use TopicBuilder to create topic. You can not give parameter to this method, since this is a Bean method.
    // This method will automatically take the bean of kafkaAdmin and do the job of creating topic.
    @Bean
    public NewTopic TopicCreator(){
        return TopicBuilder.name("testTopic")
                .build();
    }


    //For advanced feature try uysing AdminClient directly. if you want to manage the kafka container

}

package uk.co.kafka.producer.kafkaproducer.service;

public class GreetingResourceRepresentation {

    private Long id;
    private String name;

    public GreetingResourceRepresentation(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

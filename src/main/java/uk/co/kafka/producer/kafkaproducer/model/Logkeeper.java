package uk.co.kafka.producer.kafkaproducer.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

//This is a model so use @Entity and @Table
@Entity
@Table
public class Logkeeper {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String logkey;
    private String value;





}

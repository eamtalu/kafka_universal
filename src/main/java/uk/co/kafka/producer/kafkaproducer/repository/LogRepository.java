package uk.co.kafka.producer.kafkaproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.kafka.producer.kafkaproducer.model.Logkeeper;

@Repository
public interface LogRepository extends JpaRepository<Logkeeper, Long> {


}

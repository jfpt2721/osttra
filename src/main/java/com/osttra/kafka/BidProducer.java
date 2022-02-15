package com.osttra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osttra.commons.model.Bid;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static com.osttra.commons.constants.Constants.KAFKA_BOOTSTRAP_SERVER;
import static com.osttra.commons.constants.Constants.TOPIC;

public class BidProducer {
    public static void main(String args[]){
        BidProducer producer = new BidProducer();
        producer.raiseBid(new Bid("D", "copper", 500));
        producer.raiseBid(new Bid("C", "platinum", 200));
        producer.raiseBid(new Bid("A", "copper", 250));
        producer.raiseBid(new Bid("D", "copper", 100));
        producer.raiseBid(new Bid("B", "platinum", 100));
    }

    public void raiseBid(Bid bid) {

        Producer<String, String> producer = getKafkaProducer();

        try {
            String str = stringify(bid);
            System.out.println(str);
            producer.send(new ProducerRecord<String, String>(TOPIC,
                    bid.getCompany(), stringify(bid)));
            System.out.println("Message sent successfully");
        } catch(Exception e){ }
        finally {
            producer.close();
        }
    }

    private String stringify(Bid bid) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bid);
    }

    private static Producer<String, String> getKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer(props);
        return producer;
    }
}

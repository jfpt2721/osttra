package com.osttra.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osttra.MetalApplication;
import com.osttra.commons.model.Bid;
import com.osttra.scheduler.ScheduleEveryHour;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static com.osttra.commons.constants.Constants.KAFKA_BOOTSTRAP_SERVER;
import static com.osttra.commons.constants.Constants.TOPIC;

public class BidReceiver {


    public static void main(String args[]) throws IOException {
        MetalApplication ma = new MetalApplication();


        BidReceiver br = new BidReceiver();
        KafkaConsumer<String, String> consumer = br.getKafkaConsumer();
        System.out.println("connected");
        consumer.subscribe(Arrays.asList(TOPIC));

        ScheduleEveryHour scheduler = new ScheduleEveryHour();
        scheduler.printMinMaxBidEveryHour(ma);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                ObjectMapper mapper = new ObjectMapper();
                Bid bid = mapper.readValue(record.value(), Bid.class);
                ma.processBid(bid);
                System.out.println("Key: " + record.key() + ", Value:" + record.value());
                System.out.println("Partition:" + record.partition() + ",Offset:" + record.offset());
            }
        }
    }

    private KafkaConsumer<String, String> getKafkaConsumer() {
        String groupId = "bidProcessor";
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        return consumer;
    }
}


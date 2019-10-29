package com.softxide.riders.messaging;

import java.util.Properties;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softxide.riders.util.RiderConfig;
import com.softxide.riders.util.RiderConstants;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GlobalProducer<K,V> {
  
  @Getter
  private KafkaProducer<K, V> kafkaFullAckProducer;

  @Autowired
  private RiderConfig config;
  Logger logger = LoggerFactory.getLogger(GlobalProducer.class);


  public void produceWithFullAck(String brokerTopic, V genericRecord) throws Exception {
      // key is null
      ProducerRecord<K, V> record = new ProducerRecord<K, V>(brokerTopic, genericRecord);
      try {
        logger.info("Monitoring - Starting produce....");
          Future<RecordMetadata> futureHandle = this.kafkaFullAckProducer.send(record, (metadata, exception) -> {
            logger.info("In Completion");

              if (metadata != null) {
                logger.info("Monitoring - Sent record(key=" + record.key() + " value=" + record.value()
                          + " meta(partition=" + metadata.partition() + " offset=" + metadata.offset() + ")");
              }
          });
          RecordMetadata recordMetadata = futureHandle.get();
          logger.info("Monitoring - Successfully submitted kafka msg with id : " + recordMetadata.offset());
      } catch (Exception e) {
        logger.info("Monitoring - Failed during submission kafka msg");
          if (e.getCause() != null)
            logger.error("Monitoring - " + e.getCause().toString());
          throw new RuntimeException(e.getMessage(), e.getCause());
      } finally {
          // initializer.getKafkaProducer().close();
      }
  }

  @PostConstruct
  private void initialize() {
      logger.info("Initialized global producer");
      Properties props = new Properties();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBrokerList());
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, RiderConstants.COMPRESSION_TYPE_CONFIG);
      props.put(ProducerConfig.RETRIES_CONFIG, config.getProducerRetryCount());
      props.put("acks", "all");
      kafkaFullAckProducer = new KafkaProducer<K, V>(props);
      logger.info("Initialized global producer");

  }

}

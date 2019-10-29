package com.softxide.riders.messaging;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softxide.riders.util.RiderConstants;

@Component
public class DemandProducerImpl implements MessageProducer{

  @Autowired
  private GlobalProducer<String, String> producer;
  Logger logger = LoggerFactory.getLogger(DemandProducerImpl.class);

  
  @Override
  public void produce(String message) {
    try {

      logger.info("Producing monitoring metrics  :" + message);
      producer.produceWithFullAck(RiderConstants.RIDE_DEMAND_TOPIC, message);
    } catch (Exception e) {
      logger.error("Error while producing metrics : " + e);
    }
  }
  
  @PostConstruct
  private void messageTest() {
    this.produce("hello world");
  }

}

package com.softxide.riders.messaging;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softxide.riders.util.RiderConstants;

public class DemandProducerImpl implements MessageProducer{

  @Autowired
  private DemandProducer<String, String> producer;
  private ObjectMapper mapper = new ObjectMapper();
  
  @Override
  public void produce(String message) {
    try {

      log.info("Producing monitoring metrics  :" + message);
      producer.produceWithFullAck(RiderConstants.RIDE_DEMAND_TOPIC, message);
    } catch (Exception e) {
      log.error("Error while producing metrics : " + e.getMessage());
      e.printStackTrace();
    }

  }

}

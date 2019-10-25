package com.softxide.riders.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class RiderConfig {
 
  @Value("${kafka.broker.list}")
  private String brokerList;
  
  @Value("${producer.retry.count:1}")
  private String producerRetryCount;
  
  @Value("${schema.registry.url}")
  private String schemaRegistryUrl;

}

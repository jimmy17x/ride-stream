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

  public String getBrokerList() {
    return brokerList;
  }

  public void setBrokerList(String brokerList) {
    this.brokerList = brokerList;
  }

  public String getProducerRetryCount() {
    return producerRetryCount;
  }

  public void setProducerRetryCount(String producerRetryCount) {
    this.producerRetryCount = producerRetryCount;
  }

  public String getSchemaRegistryUrl() {
    return schemaRegistryUrl;
  }

  public void setSchemaRegistryUrl(String schemaRegistryUrl) {
    this.schemaRegistryUrl = schemaRegistryUrl;
  }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.logger.client.impl;

import im.dadoo.log.Log;
import im.dadoo.logger.client.LoggerClient;
import im.dadoo.mq.producer.Producer;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author codekitten
 */
public class JmsLoggerClient implements LoggerClient {
  
  private static final Logger logger = LoggerFactory.getLogger(JmsLoggerClient.class);
  
  @Autowired
  private Producer producer;
 
  @Override
  public void send(final Log log) {
    if (log != null) {
      Map<String, Object> selector = new HashMap<>();
      selector.put("type", "log");
      this.producer.produce(log, selector);
      logger.info(String.format("发送日志成功,消息内容为:%s", log.toString()));
    }
  }
}

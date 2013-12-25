package im.dadoo.logger.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class LoggerClient {
  
  @Autowired
  private JmsTemplate jmsTemplate;

}

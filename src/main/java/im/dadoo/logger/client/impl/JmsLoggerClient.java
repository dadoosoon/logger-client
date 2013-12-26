/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.logger.client.impl;

import im.dadoo.log.Log;
import im.dadoo.logger.client.LoggerClient;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 *
 * @author codekitten
 */
@Service
public class JmsLoggerClient implements LoggerClient {
    
  @Autowired
  private JmsTemplate jmsTemplate;
  
  public void send(final Log log) {
    this.jmsTemplate.send(new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        Message message = session.createObjectMessage(log);
        message.setStringProperty("type", "log");
        return message;
      }
    });
  }
}

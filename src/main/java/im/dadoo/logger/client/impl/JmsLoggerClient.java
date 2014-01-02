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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
/**
 *
 * @author codekitten
 */
public class JmsLoggerClient implements LoggerClient {
  
  private static final Logger logger = LoggerFactory.getLogger(JmsLoggerClient.class);
  
  @Autowired
  private JmsTemplate jmsTemplate;
  
  @Autowired
  private ObjectMapper mapper;
 
  public void send(final Log log) {
    if (log != null) {
      //为了接口实现类能够访问
      final ObjectMapper tMapper = mapper;
      this.getJmsTemplate().send(new MessageCreator() {
        public Message createMessage(Session session) throws JMSException {
          try {
            String json = tMapper.writeValueAsString(log);
            Message message = session.createTextMessage(json);
            message.setStringProperty("type", "log");
            logger.info(String.format("发送log成功,时间为%s, 发送内容%s", 
                    DateFormatUtils.ISO_DATETIME_FORMAT.format(System.currentTimeMillis()), json));
            return message;
          } catch(Exception e) {
            e.printStackTrace();
            logger.error(String.format("解析log失败，log=%s", log.toString()));
            return null;
          }
        }
      });
    }
  }

  /**
   * @return the jmjsonTemplate
   */
  public JmsTemplate getJmsTemplate() {
    return jmsTemplate;
  }

  /**
   * @param jmsTemplate the jmjsonTemplate to jsonet
   */
  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }
  
}

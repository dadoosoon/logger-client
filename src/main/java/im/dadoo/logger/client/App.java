/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.logger.client;

import java.net.Socket;
import java.util.Enumeration;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.SessionCallback;

import org.springframework.stereotype.Component;

/**
 *
 * @author codekitten
 */
@Component
public class App {
  
  @Autowired
  private JmsTemplate jmsTemplate;
  
  public void send(final Integer i) {

    this.jmsTemplate.send(new MessageCreator() {
      
      public Message createMessage(Session session) throws JMSException {
        Message message = session.createObjectMessage(i);
        message.set
      }
    });
  }
  
  public void browse() {
    
    this.jmsTemplate.browse(new BrowserCallback<ActiveMQObjectMessage>() {
      public ActiveMQObjectMessage doInJms(Session session, QueueBrowser browser) throws JMSException {
        Enumeration<ActiveMQObjectMessage> e = browser.getEnumeration();
        while (e.hasMoreElements()) {
          ActiveMQObjectMessage message = e.nextElement();
          System.out.println(message.getObject());
        }
        return null;
      }
    });
  }
  public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("logger-client-context.xml");
    App app = ctx.getBean(App.class);
    app.browse();
    for (Integer i = 0; i < 100; i++) {
      //app.send(i);
      //System.out.println(i);
      
    }
  }
}

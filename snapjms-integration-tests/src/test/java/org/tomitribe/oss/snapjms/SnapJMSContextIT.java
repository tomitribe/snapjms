package org.tomitribe.oss.snapjms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.transaction.UserTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.tomitribe.oss.snapjms.api.SnapJMSContext;

@RunWith()
public class SnapJMSContextIT {
   @Resource(name = "jms/connectionFactory")
   private ConnectionFactory connectionFactory;
   @Resource
   private UserTransaction utx;
   @Inject
   private SnapJMSContext snapJMSContext;

   @Test
   public void checkContainer() throws Exception {
      Connection connection = connectionFactory.createConnection();
      connection.start();
      try {
         utx.begin();
         Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue("payload");
         MessageProducer producer = session.createProducer(destination);
         TextMessage message = (TextMessage) session.createTextMessage("payload");
         producer.send(message);
      } finally {
         utx.commit();
      }
      TextMessage rxMessage;
      try {
         utx.begin();
         Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue("payload");
         MessageConsumer consumer = session.createConsumer(destination);
         rxMessage = (TextMessage) consumer.receive(10L);
      } finally {
         utx.commit();
      }
      if (rxMessage == null) {
         fail("No message Recieved");
      } else {
         assertEquals("payload", rxMessage.getText());
      }
   }

   @Test
   public void testSnapJMSContext_send() throws Exception {
      Connection connection = connectionFactory.createConnection();
      connection.start();
      try {
         utx.begin();
         snapJMSContext.send("payload", "payload");
      } finally {
         utx.commit();
      }
      TextMessage rxMessage;
      try {
         utx.begin();
         Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue("payload");
         MessageConsumer consumer = session.createConsumer(destination);
         rxMessage = (TextMessage) consumer.receive(10L);
      } finally {
         utx.commit();
      }
      if (rxMessage == null) {
         fail("No message Recieved");
      } else {
         assertEquals("payload", rxMessage.getText());
      }
   }
}

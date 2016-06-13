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
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.openejb.junit.jee.EJBContainerRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tomitribe.oss.snapjms.api.SnapJMSContext;
import org.tomitribe.oss.snapjms.api.SnapJMSNonTransacted;

@RunWith(EJBContainerRunner.class)
public class SnapJMSContextIT {
   private static final String TEST_DESTINATION = "org.tomitribe.oss.snapjms.SnapJMSContextIT";
   private static final String TEST_PAYLOAD = "test_payload";
   @Resource(name = "jms/connectionFactory")
   private ConnectionFactory connectionFactory;
   @Resource
   private UserTransaction utx;
   @Inject
   private SnapJMSContext snapJMSContext;
   @Inject
   @SnapJMSNonTransacted
   private SnapJMSContext snapJMSContext_noTx;
   private Connection connection;

   @Before
   public void before() throws Exception {
      connection = connectionFactory.createConnection();
      connection.start();
   }

   @After
   public void after() throws Exception {
      connection.close();
   }

   @Test
   public void test_checkIntegrationContainerSanity() throws Exception {
      try {
         utx.begin();
         Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue(TEST_DESTINATION);
         MessageProducer producer = session.createProducer(destination);
         TextMessage message = (TextMessage) session.createTextMessage(TEST_PAYLOAD);
         producer.send(message);
      } finally {
         utx.commit();
      }
      TextMessage rxMessage = receiveMessage();
      if (rxMessage == null) {
         fail("No message Recieved. That means there's a problem with the Junit/Application Composer setup of this project."
               + "This needs to be fixed first before fixing any other tests.");
      } else {
         assertEquals(TEST_PAYLOAD, rxMessage.getText());
      }
   }

   @Test
   public void testSnapJMSContext_send() throws Exception {
      try {
         utx.begin();
         snapJMSContext.send(TEST_PAYLOAD, TEST_DESTINATION);
      } finally {
         utx.commit();
      }
      TextMessage rxMessage = receiveMessage();
      if (rxMessage == null) {
         fail("No message Recieved");
      } else {
         assertEquals(TEST_PAYLOAD, rxMessage.getText());
      }
   }

   @Test
   public void testSnapJMSContext_sendImmediately() throws Exception {
      assertEquals(Status.STATUS_NO_TRANSACTION, utx.getStatus());
      snapJMSContext_noTx.send(TEST_PAYLOAD, TEST_DESTINATION);
      assertEquals(Status.STATUS_NO_TRANSACTION, utx.getStatus());
      TextMessage rxMessage = receiveMessage();
      if (rxMessage == null) {
         fail("No message Recieved");
      } else {
         assertEquals(TEST_PAYLOAD, rxMessage.getText());
      }
   }

   private TextMessage receiveMessage() throws Exception {
      TextMessage rxMessage;
      try {
         utx.begin();
         Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue(TEST_DESTINATION);
         MessageConsumer consumer = session.createConsumer(destination);
         rxMessage = (TextMessage) consumer.receive(10L);
      } finally {
         utx.commit();
      }
      return rxMessage;
   }
}

package org.tomitribe.oss.snapjms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.util.UUID;

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
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

   /**
    * A test that doesn't use any of the SnapJMS API, just to make sure the standard messaging system actually works.
    */
   @Test
   public void test_checkIntegrationContainerSanity() throws Exception {
      String testPayload = UUID.randomUUID().toString();
      try {
         utx.begin();
         Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue(TEST_DESTINATION);
         MessageProducer producer = session.createProducer(destination);
         TextMessage message = (TextMessage) session.createTextMessage(testPayload);
         producer.send(message);
      } finally {
         utx.commit();
      }
      TextMessage rxMessage = receiveMessage();
      if (rxMessage == null) {
         fail("No message Recieved. That means there's a problem with the Junit/EJBContainerRunner configuration."
               + "This needs to be fixed first before fixing any other tests.");
      } else {
         assertEquals(testPayload, rxMessage.getText());
      }
   }

   @Test
   public void testSnapJMSContext_send() throws Exception {
      String testPayload = UUID.randomUUID().toString();
      try {
         utx.begin();
         snapJMSContext.send(testPayload, TEST_DESTINATION);
      } finally {
         utx.commit();
      }
      TextMessage rxMessage = receiveMessage();
      if (rxMessage == null) {
         fail("No message Recieved");
      } else {
         assertEquals(testPayload, rxMessage.getText());
      }
   }

   @Test
   public void testSnapJMSContext_sendImmediately() throws Exception {
      assertEquals(Status.STATUS_NO_TRANSACTION, utx.getStatus());
      String testPayload = UUID.randomUUID().toString();
      snapJMSContext_noTx.send(testPayload, TEST_DESTINATION);
      assertEquals(Status.STATUS_NO_TRANSACTION, utx.getStatus());
      TextMessage rxMessage = receiveMessage();
      if (rxMessage == null) {
         fail("No message Recieved");
      } else {
         assertEquals(testPayload, rxMessage.getText());
      }
   }

   @Test
   public void testSnapJMSContext_sendAsXML() throws Exception {
      TestPayloadObject testPayload = new TestPayloadObject();
      try {
         utx.begin();
         snapJMSContext.send(testPayload, TEST_DESTINATION);
      } finally {
         utx.commit();
      }
      TextMessage rxMessage = receiveMessage();
      if (rxMessage == null) {
         fail("No message Recieved");
      } else {
         assertEquals(testPayload, JAXB.unmarshal(new StringReader(rxMessage.getText()), TestPayloadObject.class));
      }
   }

   private TextMessage receiveMessage() throws Exception {
      try {
         utx.begin();
         Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue(TEST_DESTINATION);
         MessageConsumer consumer = session.createConsumer(destination);
         return (TextMessage) consumer.receive(10L);
      } finally {
         utx.commit();
      }
   }

   @XmlRootElement
   @XmlAccessorType(XmlAccessType.NONE)
   public static class TestPayloadObject {
      @XmlElement
      public String uuid;

      public TestPayloadObject() {
         uuid = UUID.randomUUID().toString();
      }

      @Override
      public int hashCode() {
         final int prime = 31;
         int result = 1;
         result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
         return result;
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj)
            return true;
         if (obj == null)
            return false;
         if (getClass() != obj.getClass())
            return false;
         TestPayloadObject other = (TestPayloadObject) obj;
         if (uuid == null) {
            if (other.uuid != null)
               return false;
         } else if (!uuid.equals(other.uuid))
            return false;
         return true;
      }
   }
}

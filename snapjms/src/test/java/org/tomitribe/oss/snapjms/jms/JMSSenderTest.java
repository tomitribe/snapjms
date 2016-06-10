package org.tomitribe.oss.snapjms.jms;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.CDIExtension;
import org.tomitribe.oss.snapjms.internal.SnapJMS;
import org.tomitribe.oss.snapjms.jms.factories.JMSDestinationFactory;
import org.tomitribe.oss.snapjms.jms.factories.JMSMessageFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(CDIExtension.class)
public class JMSSenderTest {
   @Produces
   @SnapJMS
   @Mock
   private Logger log;
   @Produces
   @SnapJMS
   @Mock
   private Session session;
   @Produces
   @Mock
   private Queue destination;
   @Produces
   @Mock
   private MessageProducer producer;
   @Produces
   @Mock
   private Message message;
   @Produces
   @SnapJMS
   @Mock
   private JMSDestinationFactory jmsDestinationFactory;
   @Produces
   @SnapJMS
   @Mock
   private JMSMessageFactory jmsMessageFactory;
   @Inject
   @SnapJMS
   private JMSSender jmsSender;

   @Test
   public void testSendMessage() throws Exception {
      String destinationName = "destinationName";
      String payload = "payload";
      when(jmsDestinationFactory.createDestination(Queue.class, destinationName, session)).thenReturn(destination);
      when(session.createProducer(destination)).thenReturn(producer);
      when(jmsMessageFactory.createMessage(payload, session)).thenReturn(message);
      jmsSender.sendMessage(Queue.class, destinationName, payload, session);
      verify(producer).send(message);
   }
}

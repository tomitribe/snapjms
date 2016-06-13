package org.tomitribe.oss.snapjms.jms.factories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
public class JMSDestinationFactoryTest {
   @Inject
   @SnapJMS
   private JMSDestinationFactory jmsDestinationFactory;
   @Mock
   private Session session;
   private String destinationName = "destinationName";

   @Test
   public void testCreateDestination_queue() throws JMSException {
      Queue destination = mock(Queue.class);
      when(session.createQueue(destinationName)).thenReturn(destination);
      assertEquals(destination, jmsDestinationFactory.createDestination(Queue.class, destinationName, session));
   }

   @Test
   public void testCreateDestination_topic() throws JMSException {
      Topic destination = mock(Topic.class);
      when(session.createTopic(destinationName)).thenReturn(destination);
      assertEquals(destination, jmsDestinationFactory.createDestination(Topic.class, destinationName, session));
   }

   @Test(expected = UnknownDestinationTypeException.class)
   public void testCreateDestination_neither() throws JMSException {
      jmsDestinationFactory.createDestination(Destination.class, destinationName, session);
   }
}

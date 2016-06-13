package org.tomitribe.oss.snapjms.jms;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.internal.slf4j.SnapJMS;
import org.tomitribe.oss.snapjms.jms.factories.JMSDestinationFactory;
import org.tomitribe.oss.snapjms.jms.factories.JMSMessageFactory;

@ApplicationScoped
public class JMSSender implements Serializable {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMS
   private Logger log;
   @Inject
   private JMSMessageFactory messageFactory;
   @Inject
   private JMSDestinationFactory jmsDestinationFactory;

   public <T extends Destination> void sendMessage(Class<T> destinationType, String destinationName, Object payload, Session session) {
      try {
         log.debug("sendMessage() destinationType:{} destinationName:{} payload:{} marshallThePayload:{} marshallThePayload:{}",
               new Object[] { destinationType, destinationName, payload, session });
         Destination destination = jmsDestinationFactory.createDestination(destinationType, destinationName, session);
         MessageProducer producer = session.createProducer(destination);
         Message message = messageFactory.createMessage(payload, session);
         producer.send(message);
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }
}

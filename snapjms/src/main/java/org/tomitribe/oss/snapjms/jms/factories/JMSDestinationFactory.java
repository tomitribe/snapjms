package org.tomitribe.oss.snapjms.jms.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.tomitribe.oss.snapjms.internal.SnapJMS;

@SnapJMS
@ApplicationScoped
public class JMSDestinationFactory {
   public <T extends Destination> T createDestination(Class<T> destinationType, String destinationName, Session session) {
      try {
         T destination;
         if (destinationType.equals(Queue.class)) {
            destination = destinationType.cast(session.createQueue(destinationName));
         } else if (destinationType.equals(Topic.class)) {
            destination = destinationType.cast(session.createTopic(destinationName));
         } else {
            throw new UnknownDestinationTypeException();
         }
         return destination;
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }
}

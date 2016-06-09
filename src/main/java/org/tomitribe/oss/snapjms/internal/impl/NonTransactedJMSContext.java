package org.tomitribe.oss.snapjms.internal.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Queue;
import javax.jms.Session;

import org.tomitribe.oss.snapjms.api.SnapJMSContext;
import org.tomitribe.oss.snapjms.api.SnapNonTransacted;
import org.tomitribe.oss.snapjms.jms.JMSSender;
import org.tomitribe.oss.snapjms.marshalling.CompositeMarshaller;

@SnapNonTransacted
@ApplicationScoped
public class NonTransactedJMSContext implements SnapJMSContext {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapNonTransacted
   private Session session;
   @Inject
   private JMSSender sender;
   @Inject
   private CompositeMarshaller compositeMarshaller;

   @Override
   public void send(Object payload) {
      send(payload, payload.getClass().getName());
   }

   @Override
   public void send(Object payload, String destination) {
      sender.sendMessage(Queue.class, destination, compositeMarshaller.marshall(payload), session);
   }
}

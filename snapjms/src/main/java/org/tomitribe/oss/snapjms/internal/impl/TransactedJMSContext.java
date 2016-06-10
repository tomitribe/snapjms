package org.tomitribe.oss.snapjms.internal.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.jms.Queue;
import javax.jms.Session;

import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.api.SnapJMSContext;
import org.tomitribe.oss.snapjms.api.SnapJMSTransacted;
import org.tomitribe.oss.snapjms.jms.JMSSender;
import org.tomitribe.oss.snapjms.marshalling.CompositeMarshaller;

@Default
@SnapJMSTransacted
@ApplicationScoped
public class TransactedJMSContext implements SnapJMSContext {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMSTransacted
   private Session session;
   @Inject
   @SnapJMS
   private JMSSender sender;
   @Inject
   @SnapJMS
   private CompositeMarshaller compositeMarshaller;

   @Override
   public void send(Object payload) {
      send(payload, payload.getClass().getName());
   }

   @Override
   public void send(Object payload, String destination) {
      payload = compositeMarshaller.marshall(payload);
      sender.sendMessage(Queue.class, destination, payload, session);
   }
}

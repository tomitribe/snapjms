package org.tomitribe.oss.snapjms.internal.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.jms.Queue;
import javax.jms.Session;

import org.tomitribe.oss.snapjms.api.SnapJMSContext;
import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;
import org.tomitribe.oss.snapjms.api.SnapJMSNonTransacted;
import org.tomitribe.oss.snapjms.jms.JMSSender;

@SnapJMSNonTransacted
@ApplicationScoped
public class NonTransactedJMSContext implements SnapJMSContext {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMSNonTransacted
   private Provider<Session> session;
   @Inject
   private JMSSender sender;
   @Inject
   private SnapJMSMarshaller snapJMSMarshaller;

   @Override
   public void send(Object payload) {
      send(payload, payload.getClass().getName());
   }

   @Override
   public void send(Object payload, String destination) {
      sender.sendMessage(Queue.class, destination, snapJMSMarshaller.marshall(payload), session.get());
   }
}

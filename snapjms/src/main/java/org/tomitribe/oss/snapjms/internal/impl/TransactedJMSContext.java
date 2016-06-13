package org.tomitribe.oss.snapjms.internal.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.jms.Queue;
import javax.jms.Session;

import org.tomitribe.oss.snapjms.api.SnapJMSContext;
import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;
import org.tomitribe.oss.snapjms.api.SnapJMSTransacted;
import org.tomitribe.oss.snapjms.jms.JMSSender;

@Default
@SnapJMSTransacted
@ApplicationScoped
public class TransactedJMSContext implements SnapJMSContext {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMSTransacted
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
      payload = snapJMSMarshaller.marshall(payload);
      sender.sendMessage(Queue.class, destination, payload, session.get());
   }
}

package org.tomitribe.oss.snapjms.jms.factories;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.tomitribe.oss.snapjms.api.SnapJMS;

@SnapJMS
@ApplicationScoped
public class JMSMessageFactory implements Serializable {
   private static final long serialVersionUID = 1L;

   /**
    * Create a message, autoselect the proper type, and set the payload.
    */
   public Message createMessage(Object payload, Session session) {
      // TODO finish autoSelect
      try {
         Message message = null;
         if (payload instanceof CharSequence) {
            message = session.createTextMessage(payload.toString());
         } else {
            throw new UnsupportedPayloadException();
         }
         return message;
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * Create a specific type of message, and attempt to set payload
    */
   public <T extends Message> T createMessage(Class<T> messageType, Session session, Object payload) {
      // TODO finish this method for every subclass of Message
      T message;
      try {
         switch (messageType.getName()) {
         case "javax.jms.TextMessage":
            message = messageType.cast(session.createTextMessage(payload.toString()));
            break;
         default:
            throw new UnknownMessageTypeException();
         }
         return message;
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }
}

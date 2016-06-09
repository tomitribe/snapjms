package org.tomitribe.oss.snapjms.jms.factories;

public class UnknownMessageTypeException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public UnknownMessageTypeException() {
      super("The class you passed into this method is a subtype of javax.jms.Message, "
            + "but is not part of the standard JMS API and therefore cannot be constructed by this API");
   }
}

package org.tomitribe.oss.snapjms.jms.factories;

public class UnknownDestinationTypeException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public UnknownDestinationTypeException() {
      super("Destination must be javax.jms.Topic or javax.jms.Queue");
   }
}

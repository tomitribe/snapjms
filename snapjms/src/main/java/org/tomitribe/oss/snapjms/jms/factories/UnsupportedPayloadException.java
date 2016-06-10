package org.tomitribe.oss.snapjms.jms.factories;

public class UnsupportedPayloadException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public UnsupportedPayloadException() {
      // TODO update this message as more types are added
      super("The payload you passed in did not match a pattern to produce a certain message type. It should be a CharSequence (like a String)");
   }
}

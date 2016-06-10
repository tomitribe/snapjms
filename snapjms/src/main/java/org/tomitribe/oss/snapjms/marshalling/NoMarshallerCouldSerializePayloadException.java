package org.tomitribe.oss.snapjms.marshalling;

public class NoMarshallerCouldSerializePayloadException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public NoMarshallerCouldSerializePayloadException(Object payload) {
      super("NoMarshallerCouldSerializePayloadException of payload:" + payload.getClass() + "\r\n:" + payload);
   }
}

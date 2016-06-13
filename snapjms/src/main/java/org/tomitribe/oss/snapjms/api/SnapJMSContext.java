package org.tomitribe.oss.snapjms.api;

import java.io.Serializable;

public interface SnapJMSContext extends Serializable {
   /**
    * Serializes the object via the default/SnapJMSMarshaller and sends to result to the queue named `payload.getClass.getName()`
    * 
    * @param payload The contents of the message, which will be transformed to XML
    */
   void send(Object payload);

   /**
    * Serializes the object via the default/configured SnapJMSMarshaller and sends the the destination `payload.getClass.getName()`
    * 
    * @param payload The contents of the message, which will be transformed to XML
    * @param destination The destination queue name
    */
   void send(Object payload, String destination);
}

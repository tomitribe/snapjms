package org.tomitribe.oss.snapjms.api;

import java.io.Serializable;

public interface SnapJMSContext extends Serializable {
   /**
    * Serializes the object as XML and sends to the queue named `payload.getClass.getName()`
    * 
    * @param payload The contents of the message, which will be transformed to XML
    */
   void send(Object payload);

   /**
    * Serializes the object as XML and sends to the destination `payload.getClass.getName()`
    * 
    * @param payload The contents of the message, which will be transformed to XML
    * @param destination The destination queue name
    */
   void send(Object payload, String destination);
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tomitribe.oss.snapjms.jms.factories;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

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
         if (messageType.isAssignableFrom(TextMessage.class)) {
            message = messageType.cast(session.createTextMessage(payload.toString()));
         } else {
            throw new UnknownMessageTypeException();
         }
         return message;
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }
}

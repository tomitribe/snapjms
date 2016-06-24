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
package org.tomitribe.oss.snapjms.jms;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.jms.factories.JMSDestinationFactory;
import org.tomitribe.oss.snapjms.jms.factories.JMSMessageFactory;

@ApplicationScoped
public class JMSSender implements Serializable {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMS
   private Logger log;
   @Inject
   private JMSMessageFactory messageFactory;
   @Inject
   private JMSDestinationFactory jmsDestinationFactory;

   public <T extends Destination> void sendMessage(Class<T> destinationType, String destinationName, Object payload, Session session) {
      try {
         log.debug("sendMessage() destinationType:{} destinationName:{} payload:{} marshallThePayload:{} marshallThePayload:{}",
               new Object[] { destinationType, destinationName, payload, session });
         Destination destination = jmsDestinationFactory.createDestination(destinationType, destinationName, session);
         MessageProducer producer = session.createProducer(destination);
         Message message = messageFactory.createMessage(payload, session);
         producer.send(message);
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }
}

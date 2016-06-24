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
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

@ApplicationScoped
public class JMSDestinationFactory implements Serializable {
   private static final long serialVersionUID = 1L;

   public <T extends Destination> T createDestination(Class<T> destinationType, String destinationName, Session session) {
      try {
         T destination;
         if (destinationType.equals(Queue.class)) {
            destination = destinationType.cast(session.createQueue(destinationName));
         } else if (destinationType.equals(Topic.class)) {
            destination = destinationType.cast(session.createTopic(destinationName));
         } else {
            throw new UnknownDestinationTypeException();
         }
         return destination;
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }
}

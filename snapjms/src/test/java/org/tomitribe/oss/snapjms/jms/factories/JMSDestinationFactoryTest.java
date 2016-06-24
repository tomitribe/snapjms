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
 */package org.tomitribe.oss.snapjms.jms.factories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
public class JMSDestinationFactoryTest {
   @Inject
   private JMSDestinationFactory jmsDestinationFactory;
   @Mock
   private Session session;
   private String destinationName = "destinationName";

   @Test
   public void testCreateDestination_queue() throws JMSException {
      Queue destination = mock(Queue.class);
      when(session.createQueue(destinationName)).thenReturn(destination);
      assertEquals(destination, jmsDestinationFactory.createDestination(Queue.class, destinationName, session));
   }

   @Test
   public void testCreateDestination_topic() throws JMSException {
      Topic destination = mock(Topic.class);
      when(session.createTopic(destinationName)).thenReturn(destination);
      assertEquals(destination, jmsDestinationFactory.createDestination(Topic.class, destinationName, session));
   }

   @Test(expected = UnknownDestinationTypeException.class)
   public void testCreateDestination_neither() throws JMSException {
      jmsDestinationFactory.createDestination(Destination.class, destinationName, session);
   }
}

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.jms.factories.JMSDestinationFactory;
import org.tomitribe.oss.snapjms.jms.factories.JMSMessageFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
public class JMSSenderTest {
   @Produces
   @SnapJMS
   @Mock
   private Logger log;
   @Produces
   @Mock
   private Session session;
   @Produces
   @Mock
   private Queue destination;
   @Produces
   @Mock
   private MessageProducer producer;
   @Produces
   @Mock
   private Message message;
   @Produces
   @Mock
   private JMSDestinationFactory jmsDestinationFactory;
   @Produces
   @Mock
   private JMSMessageFactory jmsMessageFactory;
   @Inject
   private JMSSender jmsSender;

   @Test
   public void testSendMessage() throws Exception {
      String destinationName = "destinationName";
      String payload = "payload";
      when(jmsDestinationFactory.createDestination(Queue.class, destinationName, session)).thenReturn(destination);
      when(session.createProducer(destination)).thenReturn(producer);
      when(jmsMessageFactory.createMessage(payload, session)).thenReturn(message);
      jmsSender.sendMessage(Queue.class, destinationName, payload, session);
      verify(producer).send(message);
   }
}

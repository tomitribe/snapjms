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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
public class JMSMessageFactoryTest {
   @Produces
   @Mock
   private Session session;
   @Produces
   @Mock
   private TextMessage textMessage;
   @Inject
   private JMSMessageFactory jmsMessageFactory;

   @Before
   public void before() throws Exception {
      when(session.createTextMessage(any(String.class))).thenReturn(textMessage);
   }

   @Test
   public void testCreateMessage_text() throws Exception {
      assertEquals(textMessage, jmsMessageFactory.createMessage("payload", session));
   }

   @Test(expected = UnsupportedPayloadException.class)
   public void testCreateMessage_cantChoose() throws Exception {
      jmsMessageFactory.createMessage(new Object(), session);
   }

   @Test
   public void testCreateMessage2_text() throws Exception {
      assertEquals(textMessage, jmsMessageFactory.createMessage(TextMessage.class, session, "payload"));
   }

   @Test(expected = UnknownMessageTypeException.class)
   public void testCreateMessage2_unknownMessageType() throws Exception {
      jmsMessageFactory.createMessage(UnknownMessage.class, session, "payload");
   }

   private abstract class UnknownMessage implements Message {
   }
}

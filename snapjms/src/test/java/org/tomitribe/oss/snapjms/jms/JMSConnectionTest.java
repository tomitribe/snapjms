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
 */package org.tomitribe.oss.snapjms.jms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
public class JMSConnectionTest {
   @Produces
   @SnapJMS
   @Mock
   private Logger log;
   @Produces
   @Mock
   private ConnectionFactory connectionFactory;
   @Produces
   @Mock
   private Connection connection;
   @Inject
   private JMSConnection jmsConnection;

   @Test
   public void testCreateJMSSession() throws Exception {
      when(connectionFactory.createConnection()).thenReturn(connection);
      Session session = mock(Session.class);
      when(connection.createSession(true, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
      assertEquals(session, jmsConnection.createJMSSession());
      verify(connectionFactory).createConnection();
      verifyNoMoreInteractions(connectionFactory);
      verify(connection).start();
   }
}

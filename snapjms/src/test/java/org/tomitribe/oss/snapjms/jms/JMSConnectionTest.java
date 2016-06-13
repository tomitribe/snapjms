package org.tomitribe.oss.snapjms.jms;

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

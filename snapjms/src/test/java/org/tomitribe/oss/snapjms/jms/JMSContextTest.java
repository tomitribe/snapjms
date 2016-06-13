package org.tomitribe.oss.snapjms.jms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Session;
import javax.transaction.TransactionSynchronizationRegistry;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
public class JMSContextTest {
   @Produces
   @SnapJMS
   @Mock
   private Logger log;
   @Produces
   @Mock
   private JMSConnection jmsConnection;
   @Produces
   @Mock
   private Session session;
   @Produces
   @Mock
   private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
   @Inject
   private JMSContext jmsContext;

   @Before
   public void before() throws Exception {
      when(jmsConnection.createJMSSession()).thenReturn(session);
   }

   @Test
   public void testGetSession() throws Exception {
      assertEquals(session, jmsContext.getSession());
      verify(jmsConnection).createJMSSession();
      verify(transactionSynchronizationRegistry).getResource(jmsContext.getKey());
      verify(transactionSynchronizationRegistry).putResource(jmsContext.getKey(), session);
      verifyNoMoreInteractions(transactionSynchronizationRegistry);
   }

   @Test
   public void testGetSession_alreadyExists() throws Exception {
      when(transactionSynchronizationRegistry.getResource(jmsContext.getKey())).thenReturn(session);
      assertEquals(session, jmsContext.getSession());
      verify(transactionSynchronizationRegistry).getResource(jmsContext.getKey());
      verifyNoMoreInteractions(transactionSynchronizationRegistry);
   }

   @SuppressWarnings("unchecked")
   @Test(expected = NoActiveTransactionException.class)
   public void testGetSession_noTransaction() {
      when(transactionSynchronizationRegistry.getResource(any(String.class))).thenThrow(NoActiveTransactionException.class);
      jmsContext.getSession();
   }
}

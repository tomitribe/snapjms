package org.tomitribe.oss.snapjms.jms;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Session;
import javax.transaction.TransactionSynchronizationRegistry;

import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.api.SnapJMSTransacted;

/**
 * Open a single JMS Session for the current transaction
 */
@ApplicationScoped
public class JMSContext implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String key;
   @Inject
   @SnapJMS
   private Logger log;
   @Inject
   private JMSConnection jmsConnection;
   @Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
   private TransactionSynchronizationRegistry transactionSynchronizationRegistry;

   public JMSContext() {
      key = getClass().getName() + ".session";
   }

   @Produces
   @Default
   @SnapJMSTransacted
   public Session getSession() {
      try {
         Session session = (Session) transactionSynchronizationRegistry.getResource(key);
         if (session == null) {
            session = jmsConnection.createJMSSession();
            log.debug("getSession() storing JMS session in transactionSynchronizationRegistry");
            transactionSynchronizationRegistry.putResource(key, session);
         }
         log.debug("getSession() returning session:{}", session);
         return session;
      } catch (IllegalStateException e) {
         throw new NoActiveTransactionException(e);
      }
   }

   String getKey() {
      return key;
   }
}

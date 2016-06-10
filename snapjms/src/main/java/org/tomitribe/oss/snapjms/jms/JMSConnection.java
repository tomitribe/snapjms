package org.tomitribe.oss.snapjms.jms;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.api.SnapJMSNonTransacted;

/**
 * Open a single JMS connection for the whole application
 */
@SnapJMS
@ApplicationScoped
public class JMSConnection implements Serializable {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMS
   private Logger log;
   @Resource(name = "jms/connectionFactory")
   private ConnectionFactory connectionFactory;
   private Connection connection;

   @PostConstruct
   void postConstruct() {
      log.info("postConstruct() Creating connection");
      try {
         connection = connectionFactory.createConnection();
         connection.start();
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }

   @PreDestroy
   void preDestroy() {
      log.info("preDestroy() Closing connection");
      try {
         connection.close();
      } catch (JMSException e) {
         throw new RuntimeException(e);
      } finally {
         connection = null;
      }
   }

   @Produces
   @SnapJMSNonTransacted
   public Session createJMSSession() {
      try {
         log.debug("createNewJMSSession()");
         return connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
      } catch (JMSException e) {
         throw new RuntimeException(e);
      }
   }
}

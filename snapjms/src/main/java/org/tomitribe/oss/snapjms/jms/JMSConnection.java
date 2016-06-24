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
@ApplicationScoped
public class JMSConnection implements Serializable {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMS
   private Logger log;
   @Resource(name = "jms/connectionFactory")
   private ConnectionFactory connectionFactory;
   private transient Connection connection;

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

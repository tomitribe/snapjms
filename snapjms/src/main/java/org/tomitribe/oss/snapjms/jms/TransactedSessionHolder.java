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
public class TransactedSessionHolder implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String key;
   @Inject
   @SnapJMS
   private Logger log;
   @Inject
   private JMSConnection jmsConnection;
   @Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
   private TransactionSynchronizationRegistry transactionSynchronizationRegistry;

   public TransactedSessionHolder() {
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

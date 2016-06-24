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
package org.tomitribe.oss.snapjms.internal.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.jms.Queue;
import javax.jms.Session;

import org.tomitribe.oss.snapjms.api.SnapJMSContext;
import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;
import org.tomitribe.oss.snapjms.api.SnapJMSNonTransacted;
import org.tomitribe.oss.snapjms.jms.JMSSender;

@SnapJMSNonTransacted
@ApplicationScoped
public class NonTransactedJMSContext implements SnapJMSContext {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMSNonTransacted
   private Provider<Session> session;
   @Inject
   private JMSSender sender;
   @Inject
   private SnapJMSMarshaller snapJMSMarshaller;

   @Override
   public void send(Object payload) {
      send(payload, payload.getClass().getName());
   }

   @Override
   public void send(Object payload, String destination) {
      sender.sendMessage(Queue.class, destination, snapJMSMarshaller.marshall(payload), session.get());
   }
}

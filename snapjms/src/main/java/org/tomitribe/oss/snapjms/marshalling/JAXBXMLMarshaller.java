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
package org.tomitribe.oss.snapjms.marshalling;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;
import org.tomitribe.oss.snapjms.api.jaxbconfig.JAXBContextProperties;
import org.tomitribe.oss.snapjms.api.jaxbconfig.JAXBMarshallerProperties;

@SnapJMS
@ApplicationScoped
public class JAXBXMLMarshaller implements SnapJMSMarshaller {
   private static final long serialVersionUID = 1L;
   @Inject
   @JAXBContextProperties
   private Map<String, Object> jaxbContextProperties;
   @Inject
   @JAXBMarshallerProperties
   private Map<String, Object> jaxbMarshallerProperties;

   @Override
   public String marshall(Object payload) {
      try {
         JAXBContext context = JAXBContext.newInstance(new Class<?>[] { payload.getClass() }, jaxbContextProperties);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         Marshaller marshaller = context.createMarshaller();
         for (String key : jaxbMarshallerProperties.keySet()) {
            marshaller.setProperty(key, jaxbMarshallerProperties.get(key));
         }
         marshaller.marshal(payload, baos);
         return baos.toString();
      } catch (JAXBException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public boolean isInterestedIn(Class<?> payloadClass) {
      return payloadClass.isAnnotationPresent(XmlRootElement.class);
   }
}

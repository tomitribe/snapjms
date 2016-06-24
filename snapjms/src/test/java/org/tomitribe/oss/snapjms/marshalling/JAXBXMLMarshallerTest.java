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
 */package org.tomitribe.oss.snapjms.marshalling;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.api.jaxbconfig.SnapJMSJAXBXMLMarshallerConfig;

@RunWith(CdiRunner.class)
@AdditionalClasses({ AtResourceInjectionCDIExtension.class, SnapJMSJAXBXMLMarshallerConfig.class })
public class JAXBXMLMarshallerTest {
   @Inject
   @SnapJMS
   private JAXBXMLMarshaller jaxbXMLMarshaller;

   @Test
   public void testMarshall() {
      String output = jaxbXMLMarshaller.marshall(new HasAnXMLRootElement());
      output = output.replaceAll("\\r", "");
      output = output.replaceAll("\\n", "");
      assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><hasAnXMLRootElement>"
            + "    <renderedInXML>fail the unit test if not present</renderedInXML></hasAnXMLRootElement>", output);
   }

   @Test
   public void testIsInterestedIn_false() {
      jaxbXMLMarshaller.isInterestedIn(HasNoXMLRootElement.class);
   }

   @Test
   public void testIsInterestedIn_true() {
      jaxbXMLMarshaller.isInterestedIn(HasAnXMLRootElement.class);
   }

   public static class HasNoXMLRootElement {
      @XmlElement
      private String notRenderedInXML = "fail the unit test if present";
   }

   @XmlRootElement
   @XmlAccessorType(XmlAccessType.NONE)
   public static class HasAnXMLRootElement {
      private String notRenderedInXML = "fail the unit test if present";
      @XmlElement
      private String renderedInXML = "fail the unit test if not present";

      public String getNotRenderedInXML() {
         return notRenderedInXML;
      }

      public void setNotRenderedInXML(String notRenderedInXML) {
         this.notRenderedInXML = notRenderedInXML;
      }

      public String getRenderedInXML() {
         return renderedInXML;
      }

      public void setRenderedInXML(String renderedInXML) {
         this.renderedInXML = renderedInXML;
      }
   }
}

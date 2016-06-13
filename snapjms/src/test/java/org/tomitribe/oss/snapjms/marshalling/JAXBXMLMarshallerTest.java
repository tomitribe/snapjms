package org.tomitribe.oss.snapjms.marshalling;

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
import org.tomitribe.oss.snapjms.api.jaxbconfig.SnapJMSJAXBXMLMarshallerConfig;

@RunWith(CdiRunner.class)
@AdditionalClasses({ AtResourceInjectionCDIExtension.class, SnapJMSJAXBXMLMarshallerConfig.class })
public class JAXBXMLMarshallerTest {
   @Inject
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

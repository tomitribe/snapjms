package org.tomitribe.oss.snapjms.api.jaxbconfig;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.xml.bind.Marshaller;

import org.tomitribe.oss.snapjms.api.SnapJMS;

@SnapJMS
@ApplicationScoped
public class SnapJMSJAXBXMLMarshallerConfig {
   private Map<String, Object> defaultJAXBMarshallerProperties;
   private Map<String, Object> defaultJAXBContextProperties;

   @PostConstruct
   void postContruct() {
      defaultJAXBMarshallerProperties = new HashMap<>();
      defaultJAXBMarshallerProperties.put(Marshaller.JAXB_ENCODING, "UTF-8");
      defaultJAXBMarshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      defaultJAXBContextProperties = new HashMap<>();
   }

   @Produces
   @Default
   @SnapJMS
   @JAXBMarshallerProperties
   public Map<String, Object> getDefaultJAXBMarshallerProperties() {
      return defaultJAXBMarshallerProperties;
   }

   @Produces
   @Default
   @SnapJMS
   @JAXBContextProperties
   public Map<String, Object> getDefaultJAXBContextProperties() {
      return defaultJAXBContextProperties;
   }
}
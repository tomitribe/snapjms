package org.tomitribe.oss.snapjms.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;
import javax.xml.bind.Marshaller;

@ApplicationScoped
public class JAXBXMLMarshallerConfig {
   public JAXBXMLMarshallerConfig() {
      defaultJAXBMarshallerProperties = new HashMap<>();
      defaultJAXBMarshallerProperties.put(Marshaller.JAXB_ENCODING, "UTF-8");
      defaultJAXBMarshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      defaultJAXBContextProperties = new HashMap<>();
   }

   @Produces
   @Default
   @SnapJMS
   @JAXBMarshallerProperties
   private Map<String, Object> defaultJAXBMarshallerProperties;
   @Produces
   @Default
   @SnapJMS
   @JAXBContextProperties
   private Map<String, Object> defaultJAXBContextProperties;

   @Qualifier
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER })
   public @interface JAXBMarshallerProperties {
   }

   @Qualifier
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER })
   public @interface JAXBContextProperties {
   }
}

package org.tomitribe.oss.snapjms.marshalling;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.tomitribe.oss.snapjms.api.SnapMarshaller;
import org.tomitribe.oss.snapjms.internal.SnapJMS;

@SnapJMS
@ApplicationScoped
public class JAXBXMLMarshaller implements SnapMarshaller {
   @Inject
   @SnapJMS
   @JAXBContextProperties
   private Map<String, Object> jaxbContextProperties;
   @Inject
   @SnapJMS
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

   public JAXBXMLMarshaller() {
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

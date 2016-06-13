package org.tomitribe.oss.snapjms.marshalling;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;
import org.tomitribe.oss.snapjms.api.jaxbconfig.JAXBContextProperties;
import org.tomitribe.oss.snapjms.api.jaxbconfig.JAXBMarshallerProperties;

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

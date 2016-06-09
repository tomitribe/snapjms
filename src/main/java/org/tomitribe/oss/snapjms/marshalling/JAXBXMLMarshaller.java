package org.tomitribe.oss.snapjms.marshalling;

import java.io.ByteArrayOutputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import org.tomitribe.oss.snapjms.api.SnapMarshaller;
import org.tomitribe.oss.snapjms.internal.SnapJMS;

@SnapJMS
@ApplicationScoped
public class JAXBXMLMarshaller implements SnapMarshaller {
   @Override
   public String marshall(Object payload) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      JAXB.marshal(payload, baos);
      return baos.toString();
   }

   @Override
   public boolean isInterestedIn(Class<?> payloadClass) {
      return payloadClass.isAnnotationPresent(XmlRootElement.class);
   }
}

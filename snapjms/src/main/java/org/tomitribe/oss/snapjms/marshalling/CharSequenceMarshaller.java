package org.tomitribe.oss.snapjms.marshalling;

import javax.enterprise.context.ApplicationScoped;

import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;
import org.tomitribe.oss.snapjms.internal.slf4j.SnapJMS;

@SnapJMS
@ApplicationScoped
public class CharSequenceMarshaller implements SnapJMSMarshaller {
   private static final long serialVersionUID = 1L;

   @Override
   public String marshall(Object payload) {
      return payload.toString();
   }

   @Override
   public boolean isInterestedIn(Class<?> payloadClass) {
      return CharSequence.class.isAssignableFrom(payloadClass);
   }
}

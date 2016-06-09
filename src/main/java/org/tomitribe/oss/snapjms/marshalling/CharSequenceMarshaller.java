package org.tomitribe.oss.snapjms.marshalling;

import javax.enterprise.context.ApplicationScoped;

import org.tomitribe.oss.snapjms.api.SnapMarshaller;
import org.tomitribe.oss.snapjms.internal.SnapJMS;

@SnapJMS
@ApplicationScoped
public class CharSequenceMarshaller implements SnapMarshaller {
   @Override
   public String marshall(Object payload) {
      return payload.toString();
   }

   @Override
   public boolean isInterestedIn(Class<?> payloadClass) {
      return CharSequence.class.isAssignableFrom(payloadClass);
   }
}

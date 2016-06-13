package org.tomitribe.oss.snapjms.marshalling;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;

@Default
@SnapJMS
@ApplicationScoped
public class CompositeMarshaller implements SnapJMSMarshaller {
   private static final long serialVersionUID = 1L;
   @Inject
   @SnapJMS
   private Instance<SnapJMSMarshaller> marshallers;
   @Inject
   @SnapJMS
   private Logger log;

   @Override
   public String marshall(Object payload) {
      String messageText = null;
      for (SnapJMSMarshaller marshaller : marshallers) {
         if (!getClass().isAssignableFrom(marshaller.getClass()) && marshaller.isInterestedIn(payload.getClass())) {
            log.debug("marshall() selected marshaller:{}", marshaller.getClass().getName());
            messageText = marshaller.marshall(payload);
            break;
         }
      }
      if (messageText == null) {
         throw new NoMarshallerCouldSerializePayloadException(payload);
      } else {
         log.debug("marshall() returning messageText:{}", messageText);
         return messageText;
      }
   }

   @Override
   public boolean isInterestedIn(Class<?> payloadClass) {
      return true;
   }
}

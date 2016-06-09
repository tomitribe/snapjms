package org.tomitribe.oss.snapjms.marshalling;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.api.SnapMarshaller;
import org.tomitribe.oss.snapjms.internal.SnapJMS;

@Default
@ApplicationScoped
public class CompositeMarshaller implements SnapMarshaller {
   @Inject
   @SnapJMS
   private Instance<SnapMarshaller> marshallers;
   @Inject
   private Logger log;

   @Override
   public String marshall(Object payload) {
      String messageText = null;
      for (SnapMarshaller marshaller : marshallers) {
         if (!(marshaller instanceof CompositeMarshaller) && marshaller.isInterestedIn(payload.getClass())) {
            log.debug("marshall() selected marshaller:{}", marshaller.getClass().getName());
            messageText = marshaller.marshall(payload);
            break;
         }
      }
      log.debug("marshall() returning messageText:{}", messageText);
      return messageText;
   }

   @Override
   public boolean isInterestedIn(Class<?> payloadClass) {
      return true;
   }
}

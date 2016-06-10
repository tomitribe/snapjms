package org.tomitribe.oss.snapjms.api;

public interface SnapJMSMarshaller {
   String marshall(Object payload);

   boolean isInterestedIn(Class<?> payloadClass);
}

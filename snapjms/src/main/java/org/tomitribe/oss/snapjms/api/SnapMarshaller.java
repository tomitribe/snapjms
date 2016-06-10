package org.tomitribe.oss.snapjms.api;

public interface SnapMarshaller {
   String marshall(Object payload);

   boolean isInterestedIn(Class<?> payloadClass);
}

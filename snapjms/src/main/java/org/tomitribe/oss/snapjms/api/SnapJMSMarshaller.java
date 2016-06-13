package org.tomitribe.oss.snapjms.api;

import java.io.Serializable;

public interface SnapJMSMarshaller extends Serializable {
   String marshall(Object payload);

   boolean isInterestedIn(Class<?> payloadClass);
}

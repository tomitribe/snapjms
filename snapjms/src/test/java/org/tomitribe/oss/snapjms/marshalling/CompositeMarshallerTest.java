package org.tomitribe.oss.snapjms.marshalling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;
import org.tomitribe.oss.snapjms.api.SnapJMSMarshaller;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
public class CompositeMarshallerTest {
   @Produces
   @SnapJMS
   @Mock
   private Logger log;
   @Produces
   @Mock
   private SnapJMSMarshaller mockSnapMarshaller;
   @Inject
   private CompositeMarshaller compositeMarshaller;

   @Test(expected = NoMarshallerCouldSerializePayloadException.class)
   public void testMarshall_noneWilling() {
      when(mockSnapMarshaller.isInterestedIn(any(Class.class))).thenReturn(Boolean.FALSE);
      compositeMarshaller.marshall("payload");
   }

   @Test
   public void testMarshall_testMarshaller() {
      when(mockSnapMarshaller.isInterestedIn(any(Class.class))).thenReturn(true);
      when(mockSnapMarshaller.marshall(any())).thenReturn("marshalled_that");
      assertEquals("marshalled_that", compositeMarshaller.marshall("payload"));
   }

   @Test
   public void testIsInterestedIn() {
      assertTrue(compositeMarshaller.isInterestedIn(String.class));
   }
}

package org.tomitribe.oss.snapjms.marshalling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tomitribe.oss.snapjms.CDIExtension;
import org.tomitribe.oss.snapjms.internal.SnapJMS;

@RunWith(CdiRunner.class)
@AdditionalClasses(CDIExtension.class)
public class CharSequenceMarshallerTest {
   @Inject
   @SnapJMS
   private CharSequenceMarshaller charSequenceMarshaller;

   @Test
   public void testMarshall() {
      CharSequence sb = new CharSequence() {
         @Override
         public CharSequence subSequence(int start, int end) {
            return null;
         }

         @Override
         public int length() {
            return 0;
         }

         @Override
         public char charAt(int index) {
            return 0;
         }
      };
      sb = spy(sb);
      charSequenceMarshaller.marshall(sb);
   }

   @Test
   public void testIsInterestedIn_true() {
      assertTrue(charSequenceMarshaller.isInterestedIn(CharSequence.class));
   }

   @Test
   public void testIsInterestedIn_false() {
      assertFalse(charSequenceMarshaller.isInterestedIn(Object.class));
   }
}

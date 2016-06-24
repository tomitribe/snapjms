/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package org.tomitribe.oss.snapjms.marshalling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tomitribe.oss.snapjms.AtResourceInjectionCDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;

@RunWith(CdiRunner.class)
@AdditionalClasses(AtResourceInjectionCDIExtension.class)
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

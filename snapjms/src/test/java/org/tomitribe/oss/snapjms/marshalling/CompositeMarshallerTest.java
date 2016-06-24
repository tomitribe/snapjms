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
 */
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
   @SnapJMS
   @Mock
   private SnapJMSMarshaller mockSnapMarshaller;
   @Inject
   @SnapJMS
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

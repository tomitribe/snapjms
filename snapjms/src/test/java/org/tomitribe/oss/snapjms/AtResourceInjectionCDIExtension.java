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
 */package org.tomitribe.oss.snapjms;

import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.inject.Inject;

import org.apache.deltaspike.core.util.metadata.AnnotationInstanceProvider;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

public class AtResourceInjectionCDIExtension implements Extension {
   <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
      Set<AnnotatedField<? super T>> fields = pat.getAnnotatedType().getFields();
      for (AnnotatedField<? super T> field : fields) {
         if (shouldInjectionAnnotationBeAddedToField(field)) {
            AnnotatedType<T> at = pat.getAnnotatedType();
            AnnotatedTypeBuilder<T> builder = new AnnotatedTypeBuilder<T>().readFromType(at);
            Inject injectAnnotation = AnnotationInstanceProvider.of(Inject.class);
            builder.addToField(field, injectAnnotation);
            pat.setAnnotatedType(builder.create());
         }
      }
   }

   private <X> boolean shouldInjectionAnnotationBeAddedToField(AnnotatedField<? super X> field) {
      return !field.isAnnotationPresent(Inject.class) && field.isAnnotationPresent(Resource.class);
   }
}

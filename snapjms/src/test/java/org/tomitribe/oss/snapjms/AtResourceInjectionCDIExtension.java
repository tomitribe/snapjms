package org.tomitribe.oss.snapjms;

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

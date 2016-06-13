package org.tomitribe.oss.snapjms.internal.slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Produces loggers for CDI Injection
 */
@SnapJMS
@ApplicationScoped
public class SLF4JLoggerProducer {
   private static final Logger log = LoggerFactory.getLogger(SLF4JLoggerProducer.class);

   @Produces
   @SnapJMS
   protected Logger produceLogger(InjectionPoint ip) {
      Class<?> requestingClass = ip.getMember().getDeclaringClass();
      log.debug("produceLogger() requestingClass:{}", requestingClass.getName());
      return new SLogger(requestingClass);
   }
}

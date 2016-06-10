package org.tomitribe.oss.snapjms;

import static org.junit.Assert.assertTrue;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.tomitribe.oss.snapjms.api.SnapJMSContext;

public class SnapJMSContextIT {
   @Inject
   private SnapJMSContext snapJMSContext;

   @Before
   public void before() throws Exception {
      EJBContainer.createEJBContainer().getContext().bind("inject", this);
   }

   @Test
   public void testIntegration() throws Exception {
      assertTrue("Integration Tests rung", true);
   }

   @Test
   public void testSnapJMSContext_send() throws Exception {
      snapJMSContext.send("payload", "org.tomitribe.oss.snapjms.SnapJMSContextIT");
   }
}
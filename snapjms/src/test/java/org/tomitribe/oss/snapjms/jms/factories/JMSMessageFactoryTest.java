package org.tomitribe.oss.snapjms.jms.factories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.tomitribe.oss.snapjms.CDIExtension;
import org.tomitribe.oss.snapjms.api.SnapJMS;

@RunWith(CdiRunner.class)
@AdditionalClasses(CDIExtension.class)
public class JMSMessageFactoryTest {
   @Produces
   @SnapJMS
   @Mock
   private Session session;
   @Produces
   @Mock
   private TextMessage textMessage;
   @Inject
   @SnapJMS
   private JMSMessageFactory jmsMessageFactory;

   @Before
   public void before() throws Exception {
      when(session.createTextMessage(any(String.class))).thenReturn(textMessage);
   }

   @Test
   public void testCreateMessage_text() throws Exception {
      assertEquals(textMessage, jmsMessageFactory.createMessage("payload", session));
   }

   @Test(expected = UnsupportedPayloadException.class)
   public void testCreateMessage_cantChoose() throws Exception {
      jmsMessageFactory.createMessage(new Object(), session);
   }

   @Test
   public void testCreateMessage2_text() throws Exception {
      assertEquals(textMessage, jmsMessageFactory.createMessage(TextMessage.class, session, "payload"));
   }

   @Test(expected = UnknownMessageTypeException.class)
   public void testCreateMessage2_unknownMessageType() throws Exception {
      jmsMessageFactory.createMessage(UnknownMessage.class, session, "payload");
   }

   private abstract class UnknownMessage implements Message {
   }
}

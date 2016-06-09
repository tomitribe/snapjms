package org.tomitribe.oss.snapjms.jms;

import javax.ejb.ApplicationException;

@ApplicationException(inherited = true)
public class NoActiveTransactionException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public NoActiveTransactionException(Throwable cause) {
      super("In order to use JMSContext, you must start a transaction before calling JMSContext,"
            + " since it behaves in a TransactionScoped manner", cause);
   }
}

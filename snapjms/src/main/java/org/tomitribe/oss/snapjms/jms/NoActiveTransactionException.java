package org.tomitribe.oss.snapjms.jms;

import javax.ejb.ApplicationException;

@ApplicationException(inherited = true)
public class NoActiveTransactionException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public NoActiveTransactionException(Throwable cause) {
      super("In order to use TransactedSessionHolder, you must start a transaction before calling TransactedSessionHolder,"
            + " since it behaves in a TransactionScoped manner", cause);
   }
}

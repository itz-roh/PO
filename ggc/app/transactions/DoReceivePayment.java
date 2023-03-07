package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Transaction;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.exception.BadEntryException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
   addIntegerField("id", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException, UnknownTransactionKeyException {
   try{
    _receiver.registerPayment(integerField("id"));
   } catch (BadEntryException bee){
      throw new UnknownTransactionKeyException(integerField("id"));
    }
  }

}

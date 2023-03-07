package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Transaction;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.exception.BadEntryException;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("id", Message.requestTransactionKey());
  }

  @Override
  public void execute() throws CommandException , UnknownTransactionKeyException {
    Transaction p;
    try{
      p = _receiver.getTransaction(integerField("id"));
    } catch (BadEntryException bee){
      throw new UnknownTransactionKeyException(integerField("id"));
    }
    _display.addLine(p.toString());
    _display.display();
  }

}

package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Transaction;
import ggc.core.Partner;
import ggc.core.Sale;

import java.util.ArrayList;
import java.util.Collection;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.BadEntryException;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    Partner partner;
    try {
      partner = _receiver.getPartner(stringField("idPartner"));
    }  catch (BadEntryException e) {
      throw new UnknownPartnerKeyException(stringField("idPartner"));
    }
    Collection<Transaction> transactions = new ArrayList<Transaction>();
    transactions.addAll(partner.getSales());
    transactions.removeIf(s -> s.getPaymentDate() == null);
    for(Transaction iter: transactions){
      _display.addLine(iter.toString());
    }
    _display.display();
  }

}

package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Sale;
import ggc.core.Partner;
import java.util.Collection;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.BadEntryException;


/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
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
    Collection<Sale> sales = partner.getSales();
    for(Sale iter: sales){
      _display.addLine(iter.toString());
    }
    _display.display();
  }

}

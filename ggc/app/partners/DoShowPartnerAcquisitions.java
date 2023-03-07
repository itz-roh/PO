package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Acquisition;
import ggc.core.Partner;
import java.util.Collection;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.BadEntryException;


/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {
    Partner partner;
    try {
      partner = _receiver.getPartner(stringField("idPartner"));
    }  catch (BadEntryException e) {
      throw new UnknownPartnerKeyException(stringField("idPartner"));
    }
    Collection<Acquisition> acquisitions = partner.getAcquisitions();
    for(Acquisition iter: acquisitions){
      _display.addLine(iter.toString());
    }
    _display.display();
  }
}

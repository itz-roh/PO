package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.BadEntryException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("id", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException , UnknownPartnerKeyException {
    Partner p;
    try{
      p = _receiver.getPartner(stringField("id"));
    } catch (BadEntryException bee){
      throw new UnknownPartnerKeyException(stringField("id"));
    }
    _display.addLine(p.toString());
    _display.display();
  }

}

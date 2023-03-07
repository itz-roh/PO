package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;

/**
 * Show partner.
 */
class DoShowPartnerLessBuyer extends Command<WarehouseManager> {

    DoShowPartnerLessBuyer(WarehouseManager receiver) {
    super("Parceiro menos comprador", receiver);
  }

  @Override
  public void execute() throws CommandException , UnknownPartnerKeyException {
    int units = 0;
    Partner partner;
    for(Partner iter: _receiver.getPartner()){
        if(iter.getUnits() < units)
            partner = iter;
        else if(units = 0)
            partner = iter;
    }
    _display.addLine(partner.UnitsoString());
    _display.display();
  }

}

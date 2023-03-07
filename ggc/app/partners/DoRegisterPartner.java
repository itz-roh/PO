package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.exception.BadEntryException;
import ggc.app.exception.DuplicatePartnerKeyException;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("id", Message.requestPartnerKey());
    addStringField("nome", Message.requestPartnerName());
    addStringField("morada", Message.requestPartnerAddress());
    
  }

  @Override
  public void execute() throws CommandException, DuplicatePartnerKeyException{
    try{
      _receiver.registerPartner(stringField("nome"), stringField("morada"), stringField("id"));
    } catch (BadEntryException bee){
      throw new DuplicatePartnerKeyException(stringField("id"));
    }
  }

}

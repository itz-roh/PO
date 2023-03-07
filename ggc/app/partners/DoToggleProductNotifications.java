package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Product;
import ggc.core.Partner;

import java.util.Collection;

import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.BadEntryException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("idProduct", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException, UnknownProductKeyException {
    Product product;
    Partner partner;
    Collection<Partner> array;
    try {
      product = _receiver.getProduct(stringField("idProduct"));
    } catch (BadEntryException bee) {
      throw new UnknownProductKeyException(stringField("idProduct"));
    }
    try {
      partner = _receiver.getPartner(stringField("idPartner"));
    } catch (BadEntryException bee) {
      throw new UnknownPartnerKeyException(stringField("idPartner"));
    }
    if(product.removeInterestedEntity(partner)){}
    else { product.addInterestedEntity(partner); }
  }

}

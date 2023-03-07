package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

/**
 * Register new partner.
 */
class DoUpdateSell extends Command<WarehouseManager> {

    DoUpdateSell(WarehouseManager receiver) {
    super("Actualizar venda", receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("IdProduct", Message.requestPartnerName());
  }

  @Override
  public void execute() throws CommandException, DuplicatePartnerKeyException{
    String partnerId = stringField("idPartner");
    String productId = stringField("idProduct");
    for(SaleByCredict iter: _receiver.getSalesByCredict())
        if(partnerId.equals(iter.getPartnerId() && productId.equals(iter.getProductId()))){
            _display.addLine(iter.toString());
            _receiver.removeNonPaidSale(iter);
        }
        _display.display();
  }
}



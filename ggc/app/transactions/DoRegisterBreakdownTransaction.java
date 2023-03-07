package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.AggregateProduct;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.BadEntryException;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("idProduct", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException, UnknownProductKeyException, UnavailableProductException {
    Partner partner;
    Product product;
    try{
      partner = _receiver.getPartner(stringField("idPartner"));
    }catch (BadEntryException bee){
      throw new UnknownPartnerKeyException(stringField("idPartner"));
    }
    try{
      product = _receiver.getProduct(stringField("idProduct"));
    }catch (BadEntryException bee){
      throw new UnknownProductKeyException(stringField("idProduct"));
    }
    try{
      _receiver.registerBreakdownSale(product,integerField("quantity"),partner);
    }catch (BadEntryException bee){
      throw new UnavailableProductException(stringField("idProduct"),integerField("quantity"),product.getQuantity());
    }
  }

}

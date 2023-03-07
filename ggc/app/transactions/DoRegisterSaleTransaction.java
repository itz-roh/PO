package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.AggregateProduct;
import ggc.core.SimpleProduct;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.UnavailableQuantityException;
import ggc.core.exception.BadEntryException;
/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addIntegerField("deadline", Message.requestPaymentDeadline());
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
      _receiver.registerSaleByCredit(product,integerField("quantity"),partner,integerField("deadline"));
   }catch (UnavailableQuantityException uqe){
      throw new UnavailableProductException(uqe.getProductId(),product.getMissingQuantity(integerField("quantity")) * uqe.getRelation() ,uqe.getStock());
    }
  }
}

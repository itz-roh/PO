package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.*;
import ggc.core.WarehouseManager;
import ggc.app.transactions.Message;
import java.util.ArrayList;
import java.util.Collection;
import ggc.core.Partner;
import ggc.core.Recipe;
import ggc.core.Product;
import ggc.core.AggregateProduct;
import ggc.core.SimpleProduct;
import ggc.core.Component;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.exception.BadEntryException;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  private ArrayList<Component> _components; 

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    _components = new ArrayList<Component>();
    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("idProduct", Message.requestProductKey());
    addRealField("price", Message.requestPrice());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException, UnknownProductKeyException {
    Partner partner;
    Product product;
    if(!_receiver.verifyIfProductExists(stringField("idProduct"))){
      Form str = new Form();
      String answer = str.requestString(Message.requestAddRecipe());
      if(!"n".equals(answer)){
        Collection<Component> array = new ArrayList<Component>();
        Integer numComponents = str.requestInteger(Message.requestNumberOfComponents());
        Double alpha = str.requestReal(Message.requestAlpha());
        while(numComponents > 0){
          String idProduct = str.requestString(Message.requestProductKey());
          try {
            if(_receiver.verifyIfProductExists(idProduct)){
            Product component = _receiver.getProduct(idProduct);
            Integer amount = str.requestInteger(Message.requestAmount());
            Component c = new Component(component, amount);
            array.add(c);
            }
          } catch (BadEntryException e) {
            throw new UnknownProductKeyException(idProduct);
          }
          numComponents--;
        }
        Recipe recipe = new Recipe(array, alpha);
        _receiver.registerAggregateProduct(stringField("idProduct"), recipe);
      }
    else{
      _receiver.registerSimpleProduct(stringField("idProduct"));
    }
  }
    try {
      partner = _receiver.getPartner(stringField("idPartner"));
    }  catch (BadEntryException e) {
      throw new UnknownPartnerKeyException(stringField("idPartner"));
    }
    try {
      product = _receiver.getProduct(stringField("idProduct"));
    } catch (BadEntryException e) {
      throw new UnknownProductKeyException(stringField("idProduct"));
    }
    product.registerBatch(realField("price"), integerField("quantity"), partner, product);
    _receiver.registerAcquisition(product, integerField("quantity"), partner, integerField("quantity")*realField("price"));
    
    
    
  }
}


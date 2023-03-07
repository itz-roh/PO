package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.lookups.Message;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import ggc.core.Product;
import ggc.core.Batch;
import ggc.core.BatchComparator;


/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addRealField("price", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    Collection<Product> products = _receiver.getProducts();
    Collection<Batch> batches;
    ArrayList<Batch> array = new ArrayList<Batch>();
    Double priceLimit = realField("price");
    for(Product iter: products){
      batches = iter.getBatches();
      for(Batch b: batches){
        if(b.getPrice() < priceLimit)
          array.add(b);
        }
    }
    Collections.sort(array, new BatchComparator());
    for(Batch b: array){
      _display.addLine(b.toString());
    }
    _display.display();
  }
}

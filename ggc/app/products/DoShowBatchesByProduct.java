package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Product;
import ggc.core.Batch;
import ggc.core.WarehouseManager;
import java.util.Collection;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.exception.BadEntryException;


/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("id", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException, UnknownProductKeyException {
    Product p;
    try {
      p = _receiver.getProduct(stringField("id"));
    } catch (BadEntryException bee) {
      throw new UnknownProductKeyException(stringField("id"));
    }
    Collection<Batch> array = p.getBatches();
    for(Batch b: array){
      _display.addLine(b.toString());
    }
    _display.display();
  }

}

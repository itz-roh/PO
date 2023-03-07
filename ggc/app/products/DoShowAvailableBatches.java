package ggc.app.products;

import java.util.Collection;
import java.util.ArrayList;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Product;
import ggc.core.Batch;

/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    Collection<Product> products = _receiver.getProducts();
    for(Product p: products){
      for(Batch b: p.getBatches())
        _display.addLine("" + b.toString());
    }
    _display.display();
  }

}

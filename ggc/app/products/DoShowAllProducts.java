package ggc.app.products;

import java.util.Collection;
import java.util.ArrayList;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Product;

/**
 * Show all products.
 */
class DoShowAllProducts extends Command<WarehouseManager> {

  DoShowAllProducts(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  @Override
  public final void execute() throws CommandException {
      Collection<Product> products = _receiver.getProducts();
    for(Product p: products){
      _display.addLine(p.toString());
    }
    _display.display();
  }

}

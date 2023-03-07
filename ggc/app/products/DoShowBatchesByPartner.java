package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import ggc.core.Batch;
import ggc.core.BatchComparator;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.BadEntryException;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("id", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    Partner partner;
    Collection<Product> products;
    ArrayList<Batch> array = new ArrayList<Batch>();
    try {
      partner  = _receiver.getPartner(stringField("id"));
      products = _receiver.getProducts();
    } catch (BadEntryException bee) {
      throw new UnknownPartnerKeyException(stringField("id"));
    }
    Collection<Batch> batches;
    for(Product iter: products){
      batches = iter.getBatches();
      for(Batch b: batches)
        if(b.getPartner().equals(partner))
          array.add(b);
    }
    Collections.sort(array, new BatchComparator());
    for(Batch b: array){
      _display.addLine(b.toString());
    }
    _display.display();
  }
} 

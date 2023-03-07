package ggc.core;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import ggc.core.exception.BadEntryException;

public class Parser implements Serializable{
  private static final long serialVersionUID = 202109192006L;
  // It could be WarehouseManager too. Or something else.
  private Warehouse _store;

  public Parser(Warehouse w) {
    _store = w;
  }

  void parseFile(String filename) throws IOException, BadEntryException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }

  private void parseLine(String line) throws BadEntryException, BadEntryException {
    String[] components = line.split("\\|");

    switch (components[0]) {
      case "PARTNER":
        parsePartner(components, line);
        break;
      case "BATCH_S":
        parseSimpleProduct(components, line);
        break;

      case "BATCH_M":
        parseAggregateProduct(components, line);
        break;
        
      default:
        throw new BadEntryException("Invalid type element: " + components[0]);
    }
  }

  //PARTNER|id|nome|endereço
  private void parsePartner(String[] components, String line) throws BadEntryException {
    if (components.length != 4)
      throw new BadEntryException("Invalid partner with wrong number of fields (4): " + line);
    
    String id = components[1];
    String name = components[2];
    String address = components[3];
    
    _store.registerPartner(name, address, id);
  }

  //BATCH_S|idProduto|idParceiro|preço|stock-actual
  private void parseSimpleProduct(String[] components, String line) throws BadEntryException {
    if (components.length != 5)
      throw new BadEntryException("Invalid number of fields (4) in simple batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);

    if (!_store.verifyIfProductExists(idProduct)){
        _store.registerSimpleProduct(idProduct);
    }
    Product product = _store.getProduct(idProduct);
    try{
			product.registerBatch(price, stock, _store.getPartner(idPartner), product);
		}catch(BadEntryException bee){
			throw new BadEntryException("O parceiro '" + idPartner + "' não existe.");
		}
    
  }
 
    
  //BATCH_M|idProduto|idParceiro|prec ̧o|stock-actual|agravamento|componente-1:quantidade-1#...#componente-n:quantidade-n
  private void parseAggregateProduct(String[] components, String line) throws BadEntryException {
    if (components.length != 7)
      throw new BadEntryException("Invalid number of fields (7) in aggregate batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    Double aggravation = Double.parseDouble(components[5]);

    if (!_store.verifyIfProductExists(idProduct)) {
      ArrayList<Component> componentsArray = new ArrayList<>();
      for (String component : components[6].split("#")) {
        String[] recipeComponent = component.split(":");
        Component comp = new Component(_store.getProduct(recipeComponent[0]),
                                         Integer.parseInt(recipeComponent[1]));
        componentsArray.add(comp);
      }
      Recipe recipe = new Recipe(componentsArray, aggravation);
      _store.registerAggregateProduct(idProduct, recipe);
    }
    Product product = _store.getProduct(idProduct);
    try{
      product.registerBatch(price, stock, _store.getPartner(idPartner), product);
    }catch(BadEntryException bee){
      throw new BadEntryException("O parceiro '" + idPartner + "' não existe.");
    }
  }
}
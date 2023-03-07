package ggc.core;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.Collection;
import java.util.Collections;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.UnavailableQuantityException;
import ggc.core.Warehouse;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();
  //FIXME define other attributes
  
  //FIXME define constructor(s)
  public WarehouseManager(){
		_filename = "";
		_warehouse = new Warehouse();
	}
	
	public WarehouseManager(String name){
		_filename = name;
		_warehouse = new Warehouse();
	}

  //FIXME define other methods

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    try {
      FileOutputStream fileOut = new FileOutputStream(_filename);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(_warehouse);
      out.close();
      fileOut.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException, IOException  {
    try {
      FileInputStream fileIn = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      _warehouse = (Warehouse) in.readObject();
      in.close();
      fileIn.close();
    } catch (FileNotFoundException fnfe) {
      throw new UnavailableFileException(filename);
   } catch (IOException i) {
      i.printStackTrace();
      return;
   } catch (ClassNotFoundException c) {
      c.printStackTrace();
      return;
   }
    _filename = filename;
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

  public Warehouse getWarehouse(){
    return _warehouse;
  }

  public String getFileName(){
    return _filename;
  }

  public Date getDate(){
    return _warehouse.getDate();
  }

  public double getBalance(){
    return _warehouse.getBalance();
  }

  public double getAccountableBalance(){
    return _warehouse.getAccountableBalance();
  }

  public Collection<Product> getProducts() {
    return _warehouse.getProducts();
  }

  public Collection<Partner> getPartners() {
    return _warehouse.getPartners();
  }

  public Collection<Partner> getSalesByCredict() {
    return _warehouse.getSalesByCredict();
  }


  public Product getProduct(String id) throws BadEntryException{
    Product p;
    try { p = _warehouse.getProduct(id);
    }catch (BadEntryException bee) {
        throw new BadEntryException("Product does not exist");
    }
    return p;
  }

   public Partner getPartner(String id) throws BadEntryException{
    Partner p;
    try { p = _warehouse.getPartner(id);
    }catch (BadEntryException bee) {
        throw new BadEntryException("Partner does not exist");
    }
    return p;
  }

  public Transaction getTransaction(int id) throws BadEntryException{
    Transaction t;
  try { t = _warehouse.getTransaction(id);;
    }catch (BadEntryException bee) {
        throw new BadEntryException("Transaction does not exist");
    }
    return t;
  }

  public boolean verifyIfProductExists(String id){
    return _warehouse.verifyIfProductExists(id);
  } 

  public void registerAggregateProduct(String product, Recipe recipe){
      _warehouse.registerAggregateProduct(product, recipe);
  }

  public void registerSimpleProduct(String product) {
    _warehouse.registerSimpleProduct(product);
  }

  public void registerAcquisition(Product p, int quantity, Partner part, double value){
    _warehouse.registerAcquisition(p, quantity,part,value);
  }

  public void registerSaleByCredit(Product p, int quantity, Partner part, int deadline) throws UnavailableQuantityException{
    try { _warehouse.registerSaleByCredit(p,quantity,part,deadline);
    }catch (UnavailableQuantityException uce) {
      throw new UnavailableQuantityException(uce.getProductId(), uce.getRelation(), uce.getStock());
    }
  }

  public void registerPayment(int id) throws BadEntryException{
    try { _warehouse.registerPayment(id);
    }catch (BadEntryException bee) {
      throw new BadEntryException("Não exite essa transação");
    }
  }

public void registerBreakdownSale(Product p, int quantity, Partner part) throws BadEntryException{ 
  try { _warehouse.registerBreakdownSale(p,quantity,part);
    }catch (BadEntryException bee) {
      throw new BadEntryException("Não tem quantidade suficiente");
    }
  }

  public void registerPartner(String n, String ad, String id) throws BadEntryException {
    try{
      _warehouse.registerPartner(n,ad,id);
    }catch (BadEntryException bee) {
      throw new BadEntryException("Partner already exists");
    }
  }

  public void removeNonPaidSale(SaleByCredict sale){
    _salesByCredit.remove(sale);
  }
}
package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)
import java.util.Collection;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  	private static final long serialVersionUID = 202109192006L;

  // FIXME define attributes
  	private Date _date;
	private int _nextTransactionId;
	private Collection<Transaction> _historic;
	private Collection<Partner> _partners;
	private Collection<Product> _products;

  // FIXME define contructor(s)
  	public Warehouse(){
		_date = new Date();
		_nextTransactionId = 0;
		_historic = new ArrayList<Transaction>();
		_partners = new ArrayList<Partner>();
		_products = new ArrayList<Product>();
	}

  // FIXME define methods
  	public Collection<Partner> getPartners(){
		return _partners;
	}

	public Partner getPartner(String id){
		for (Partner a : _partners) {
			if(a.getId().equals(id)){
				return a;
			}
		}
		return null;
	}

	public Date getDate(){
		return _date;
	}

	public int getIdTransaction(){
		return _nextTransactionId;
	}

	public void addTransaction(Transaction t){
		_historic.add(t);
	}

	public void registerPartner(String n, String ad, String id){
		Partner p = new Partner(n, ad, id);
		_partners.add(p);
	}

	public void registerSimpleProduct(String product, String partner, double price, int quantity){
		SimpleProduct sProduct = new SimpleProduct(product);
		_products.add(sProduct);
		sProduct.registerBatch(price, quantity, getPartner(partner), sProduct);
	}

	public void registerAggregateProduct(String product, String partner, Recipe recipe, double price, int quantity){
		AggregateProduct aProduct = new AggregateProduct(product, recipe);
		_products.add(aProduct);
		aProduct.registerBatch(price, quantity, getPartner(partner), aProduct);
	}

	public int getNow(){
		return Date.now();
	}

	public void addTime(int days){
		_date.addToNow(days);

	}

	public Product getProduct(String id){
		for(Product iter: _products)
			if(iter.getId().equals(id))
				return iter;
		return null;
	}

	public Collection<Product> getProducts(){
		return _products;
	}
  
   /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  	void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    //FIXME implement method
  	}

}

package ggc.core;

import java.lang.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.UnavailableQuantityException;
import ggc.core.PartnersComparator;
import ggc.core.Partner;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Date that contains information about the current time in the app. */
	private Date _now;

	/** Next transaction identifier. */
	private int _nextTransactionId;

	/** History of all the transactions made. */
	 private ArrayList<Acquisition> _acquisitions;
	 private ArrayList<SaleByCredit> _salesByCredit;
	 private ArrayList<BreakdownSale> _breakdownSales; 

	/** List of all partners. */
	private ArrayList<Partner> _partners;

	/** List of all products. */
	private ArrayList<Product> _products;

	private double _money;

	private double _moneyAccountable;

	/**
	 * Default constructor: all arrays iniciated are empty, _nextTransactionId is equal to 0,
	 * because any transactions where maid yet and a Date is inicializated.
	 */
	public Warehouse() {
		_now = new Date();
		_nextTransactionId = 0;
		_acquisitions = new ArrayList<Acquisition>();
		_salesByCredit = new ArrayList<SaleByCredit>();
		_breakdownSales  = new ArrayList<BreakdownSale>();
		_partners = new ArrayList<Partner>();
		_products = new ArrayList<Product>();
		_money = 0;
		_moneyAccountable = 0;
	}

	/**
	 * Gives the full partner's list.
	 * @return a list with all the partners.
	 */
	public Collection<Partner> getPartners() {
		return _partners;
	}

	/**8
	 * @see #getPartners() same but for products.
	 * @return a list with all the products.
	 */
	public Collection<Product> getProducts() {
		return _products;
	}

	public double getBalance(){
		return _money;
	}

	public double getAccountableBalance(){
		return _moneyAccountable;
	}

	public int getTransactionId(){
		return _nextTransactionId;
	}
	/**
	 * Gets the current date.
	 * @return the current date as a Date.
	 */
	public Date getDate() {
		return _now;
	}

	public Collection<Partner> getSalesByCredict() {
		return _salesByCredit;
	  }

	public Transaction getTransaction(int id) throws BadEntryException{
		for (Acquisition i : _acquisitions)
			if (i.getId() == id)
				return i;
		for (SaleByCredit i : _salesByCredit)
			if (i.getId() == id){
				if(! i.isPaid())
					i.getPaid(_now);
				return i;
			}
		for (BreakdownSale i : _breakdownSales)
			if (i.getId() == id)
				return i;
		throw new BadEntryException("Transaction does not exist");

	}


	/**
	 * Looks into _products to get a certain product.
	 * @param id identifier of the product.
	 * @return the product that matches that id.
	 * @throws BadEntryException
	 */
	public Product getProduct(String id) throws BadEntryException {
		for (Product iter : _products)
			if (iter.getId().equalsIgnoreCase(id))
				return iter;
		throw new BadEntryException("Product does not exist");
	}

	/**
	 * @see #getProduct(String) same but for Partners.
	 * @param id identifier of the partner.
	 * @throws BadEntryException
	 */
	public Partner getPartner(String id) throws BadEntryException {
		for (Partner a : _partners) {
			if (a.getId().equalsIgnoreCase(id)) {
				return a;
			}
		}
		throw new BadEntryException("Partner does not exist");
	}

	public void addTransactionId(){
		_nextTransactionId++;
	}
	
	/**
	 * Register a new partner.
	 * Creates it and adds it to a list of Partners.
	 * When a partner is registered he shall receive all the notifications for the products that already exists.
	 * @see ggc.core.Partner#Partner(String, String, String)
	 * @throws BadEntryException
	 */
	public void registerPartner(String n, String ad, String id) throws BadEntryException {
		if (verifyIfPartnerExists(id))
			throw new BadEntryException("Partner already exists");
		else if(n.equals(id))
			throw new BadEntryException("Partner's id cannot be equal to its name.");
		Partner p = new Partner(n, ad, id);
		_partners.add(p);
		Collections.sort(_partners, new PartnersComparator());
	}

	/**
	 *  Checks if a certain partner already exists. 
	 *  @param id name of the partner.
	 * 	@return True if there is already a partner with that id, false if not.
	 */
	public boolean verifyIfPartnerExists(String id) {
		for (Partner iter : _partners) {
			if (iter.getId().equalsIgnoreCase(id))
				return true;
		}
		return false;
	}

	/**
	 * @see #verifyIfPartnerExists(String) - same but for products.
	 * @param id name of the product.
	 */
	public boolean verifyIfProductExists(String id) {
		for (Product iter : _products) {
			if (iter.getId().equalsIgnoreCase(id))
				return true;
		}
		return false;
	}

	/**
	 * Register a simple product.
	 * Creates it and adds it to a list of Products. 
	 * For more information @see SimpleProduct
	 */
	public void registerSimpleProduct(String product) {
		SimpleProduct sProduct = new SimpleProduct(product, _partners);
		_products.add(sProduct);
		Collections.sort(_products, new ProductComparator());
	}

	/**
	 * Register an aggregate product.
	 * Creates it and adds it to a list of Products. 
	 * For more information @see AggregateProduct
	 */
	public void registerAggregateProduct(String product, Recipe recipe){
		AggregateProduct aProduct = new AggregateProduct(product, recipe, _partners);
		_products.add(aProduct);
		Collections.sort(_products, new ProductComparator());
	}

		/**
	 * Register an acquisition.
	 * Creates it and adds it to an history of Transactions. 
	 * For more information @see Acquisition
	 */
	public void registerAcquisition(Product p, int quantity, Partner part, double value){
		int id = _nextTransactionId;
		_nextTransactionId++;
		Acquisition acquisition = new Acquisition(p, quantity, part, value, id, _now);
		_acquisitions.add(acquisition);
		part.addAcquisition(acquisition, value);
		_money = _money - value;
		_moneyAccountable = _moneyAccountable - value;
	}

		/**
	 * Register a sale by credit.
	 * Creates it and adds it to an history of Transactions.
	 * For more information @see SaleByCredit
	 */
	public void registerSaleByCredit(Product p, int quantity, Partner part, int deadline) throws UnavailableQuantityException{
		int id = _nextTransactionId;
		Product missingProduct; 
		double value = 0;
		double newValue;
		if(p.getQuantity() < quantity){
			try{
				 if(p.verifyAggregation(quantity - p.getQuantity())){
				 	newValue = p.aggregation(quantity - p.getQuantity(),this);
				 	p.registerBatch(p.aggregationGetPrice(newValue)/(quantity - p.getQuantity()), quantity - p.getQuantity(), part, p);
				 }
			}catch(UnavailableQuantityException uqe){	
				throw new UnavailableQuantityException(uqe.getProductId(), uqe.getRelation(), uqe.getStock());
			}
		}

		/**Decrease quantity and delete if needed*/
		value = decreaseBatches(quantity,p);

		/**create transaction*/
		_nextTransactionId++;
		SaleByCredit sale = new SaleByCredit(p, quantity, part, value, id, deadline);
		sale.getPaid(_now);
		_salesByCredit.add(sale);
		part.addSale(sale, value);
		_moneyAccountable = _moneyAccountable + value;
	}


	public void registerPayment(int id) throws BadEntryException{
		boolean erro = true;
		double valuePaid;
		for (SaleByCredit i : _salesByCredit)
			if (i.getId() == id){
				i.setPaymentDate(_now.getDays());
				i.getPaid(_now);
				erro = false;
				valuePaid = i.getAmountPaid();
				_money = _money + valuePaid;
				_moneyAccountable = _moneyAccountable - i.getValue() + valuePaid;
				i.getPartner().addToSalesPaidValue(valuePaid);
			}
		if(erro)
			throw new BadEntryException("Não exite essa transação");
	}

	/**
	 * Register a breakdown sale.
	 * Creates it and adds it to an history of Transactions.
	 * For more information @see BreasDownSale
	 */
	public void registerBreakdownSale(Product p, int quantity, Partner part) throws BadEntryException{ 

		if(p.getQuantity() < quantity){
			throw new BadEntryException("Não tem quantidade suficiente");
		}

		BreakdownSale sale = p.breakdown(this, quantity, part);
		if(sale !=  null){
			sale.calculateDiference();
			_breakdownSales.add(sale);
			part.addSale(sale);

			/**Add money if diference is positive*/
			if(sale.getDiference() > 0){
				double diference = sale.getDiference();
				_money = _money + diference;
				_moneyAccountable = _moneyAccountable + diference;
			}

		}
	}

	double decreaseBatches(int quantity, Product p){
		int fakeQuantity = quantity;
		double value = 0;
		double lowestPrice;
		Batch lowestBatch; 

		p.addQuantity(-fakeQuantity);
			while(fakeQuantity > 0){
				lowestPrice = p.getBatches().get(0).getPrice();
				lowestBatch = p.getBatches().get(0);  
				for(Batch b: p.getBatches()){
					if(b.getPrice() < lowestPrice){
						lowestPrice = b.getPrice();
						lowestBatch = b;
					}
				}
				if(fakeQuantity >= lowestBatch.getQuantity()){
					value = value + lowestBatch.getPrice() * lowestBatch.getQuantity();
					fakeQuantity = fakeQuantity - lowestBatch.getQuantity();
					/** lowestBatch.setQuantity(0); ???*/
					p.removeBatch(lowestBatch);
				}
				else{
					value = value + lowestBatch.getPrice() * fakeQuantity;
					lowestBatch.setQuantity(lowestBatch.getQuantity() - fakeQuantity);
					fakeQuantity = 0;
				}
			}
		return value;
	}

	/**
	 * Read a given text file with some partners and some batches to start the app with a data base.
	 * @param txtfile filename to be loaded.
	 * @throws IOException
	 * @throws BadEntryException
	 */
	void importFile(String txtfile) throws IOException, BadEntryException{
		Parser file = new Parser(this);
		file.parseFile(txtfile);
		for(Partner iter: _partners)
			iter.clearNotifications();
	}
}

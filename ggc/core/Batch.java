package ggc.core;

import java.io.Serializable;

public class Batch implements Serializable{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;
	/** Amount that an unit of the product costs. */
	private double _price;
	/** Number of units of the product in the batch.  */
	private int _quantity; 
	/** Batch's provider. */
	private Partner _partner;
	/** Contains the product of the batch. */
	private Product _product;

	/**
	 * Default constructor. Creates a batch and adds it to an array in 
	 * @see ggc.core.Product 
	 * @param price it is the price of one unit of the product
	 * @param q represents the max capacity of the batch.
	 * @param p partner who provided the batch.
	 * @param prod the batch's product. 
	 */
	public Batch(double price, int q, Partner p, Product prod){
		_price = price;
		_quantity = q;
		_partner = p;
		_product = prod;
		_product.addQuantity(q);
	}

	/**
	 * Gets the amount that one unit of this batch costs.
	 * @return the price of the product.
	 */
	public double getPrice(){
		return _price;
	}

	/**
	 * Gets the available amount of stock in the batch.
	 * @return the number of units left in the batch.
	 */
	public int getQuantity(){
		return _quantity;
	}

	/**
	 * Gets the batch's provider.
	 * @return the partner that is associated to the batch. 
	 */
	public Partner getPartner(){
		return _partner;
	}

	/**
	 * Gets the batch's product.
	 * @return the product of the batch. 
	 */
	public Product getProduct(){
		return _product;
	}

	/**
	 * Updates the stock amount in the batch.
	 * @return the batch's stock amount available. 
	 */
	public void setQuantity(int q){
		_quantity = q;
	}

	/**
	 * @return the string representation of the batch.
	 */
	public String toString(){
        return _product.getId() + "|" + _partner.getId() + "|" + Math.round(_price) + "|" + _quantity;
    }
}
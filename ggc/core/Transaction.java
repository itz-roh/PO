package ggc.core;

import java.io.Serializable;

abstract public class Transaction implements Serializable{
	private static final long serialVersionUID = 202109192006L;
	private Product _product;
	private int _id;
	private Date _paymentDate;
	private double _baseValue;
	private int _quantity;

	public Transaction(int id, Product product, double value, int quant, Date now){
		_product = product;
		_id = id;    // Dado pela Warehouse _nextTransationId
		_paymentDate = new Date(now.getDays());
		_baseValue = value;
		_quantity = quant;
	}

	public Transaction(int id, Product product, double value, int quant){
		_product = product;
		_id = id;    // Dado pela Warehouse _nextTransationId
		_paymentDate = null;
		_baseValue = value;
		_quantity = quant;
	}

	public Date getPaymentDate(){
		return _paymentDate;
	}

	public void setPaymentDate(int i){
		_paymentDate = new Date(i);
	}

	public double getValue(){
		return _baseValue;
	}

	public Product getProduct(){
		return _product;
	}

	public int getId(){
		return _id;
	}

	public int getQuantity(){
		return _quantity;
	}

	public void setValue(double v){
		_baseValue = v;
	}

	abstract public String toString();
}
package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable{
	private static final long serialVersionUID = 202109192006L;
	private String _type;
	private String _idProduct;
	private Double _price;
	private Delivery _deliveryType;

	public Notification(String type, String idProd, Double price){
		_type = type;
		_idProduct = idProd;
		_price = price;
	}

	public void setDeliveryType(Delivery deliveryType){
		_deliveryType = deliveryType;
	}

	public String toString(){
		return _type + "|" + _idProduct + "|" + Math.round(_price);
	}

	
}
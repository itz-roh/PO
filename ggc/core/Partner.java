package ggc.core;

import java.util.Collection;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class Partner implements a Partner.
 */
public class Partner implements Serializable, InterestedEntity{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** String that contains information about partner's value. */
	private String _name;

	/** String that contains information about partner's address. */
	private String _address;

	/** String that contains information about partner's id. */
	private String _id;

	/** String that contains information about partner's status. */
	private String _status;

	/** double that contains information about partner's points. */
	private double _points;

	/** double that contains information about partner's acquisitions value. */
	private double _acquisitionsValue;

	/** double that contains information about partner's sales value. */
	private double _salesBaseValue;

	/** double that contains information about partner's paid sales value. */
	private double _salesPaidValue;

	/** History of all the acquisitoins made. */
	private Collection<Acquisition> _acquisitions;

	/** History of all the sales made. */
	private Collection<Sale> _sales;

	/** List of all notifications. */
	private Collection<Notification> _notifications;

	/** List of all batches that have transactions with the partner. */
	private Collection<Batch> _batches;




	/**
	 * Default constructor: all arrays iniciated are empty, _status is equal to NORMAL,
	 * _points equal to 0, acquisitionsValue equals 0, _salesPaidValue equals 0, _salesBaseValue equals 0,
	 * because any transactions were made yet
	 * and it is inicializated a String _name and a String _address and a String _id.
	 */
	public Partner(String n, String ad, String id){
		_name = n;
		_address = ad;
		_id = id;
		_status = "NORMAL";
		_points = 0;
		_acquisitionsValue = 0;
		_salesBaseValue = 0;
		_salesPaidValue = 0;
		_acquisitions = new ArrayList<Acquisition>();
		_sales = new ArrayList<Sale>();
		_notifications = new ArrayList<Notification>();
		_batches = new ArrayList<Batch>();
	}

	/**
	 * Gets the partner's name.
	 * @return the name as a String.
	 */
	public String getName(){
		return _name;
	}

	/**
	 * Gets the partner's address.
	 * @return the address as a String.
	 */
	public String getAddress(){
		return _address;
	}

	/**
	 * Gets the partner's id.
	 * @return the id as a String.
	 */
	public String getId(){
		return _id;
	}

	/**
	 * Gets the partner's status.
	 * @return the status as a String.
	 */
	public String getStatus(){
		return _status;
	}

	/**
	 * Gets the partner's points.
	 * @return the points as a double.
	 */
	public double getPoints(){
		return _points;
	}

	/**
	 * Gets the acquisitions value.
	 * @return the acquisitionsvalue as a long.
	 */
	public long getAcquisitionsValue(){
		return Math.round(_acquisitionsValue);
	}


	/**
	 * Gets the sale value.
	 * @return the salevalue as a long.
	 */
	public long getSalesBaseValue(){
		return Math.round(_salesBaseValue);
	}

	/**
	 * Gets the sale paid value.
	 * @return the salevalue as a long.
	 */
	public long getSalesPaidValue(){
		return Math.round(_salesPaidValue);
	}

	/**
	 * Set points a new value 
	 * @param p new points value.
	 */
	public void setPoints(double p){
		_points = p;
		if(_points > 2000)
			this.setStatus("SELECTION");
		if(_points > 25000)
			this.setStatus("ELITE");
	}

	/**
	 * Set status a new String 
	 * @param s new status string.
	 */
	public void setStatus(String s){
		_status = s;
	}

	/**
	 * Add a acquisition to acquisitions array
	 * Add a value to acquisitions value
	 * @param v double value to add to aquisitions value.
	 * @param a acquisition to add to the acquisitions list.
	 */
	public void addAcquisition(Acquisition a, double v){
		_acquisitions.add(a);
		_acquisitionsValue += v;
	}

	/**
	 * Add a sale to sale array
	 * Add a value to sale value
	 * @param v double value to add to sale value.
	 * @param sale sale to add to the sale list.
	 */
	public void addSale(Sale sale, double v){
		_sales.add(sale);
		_salesBaseValue += v;
	}

	/**
	 * Add a sale to sale array
	 * @param sale sale to add to the sale list.
	 */
	public void addSale(Sale sale){
		_sales.add(sale);
		
	}

	/**
	 * Add a batch to batch array
	 * @param b batch to add to the batch list.
	 */
	public void addBatch(Batch b){
		_batches.add(b);
	}

	/**
	 * Add a value to sale paid value
	 * @param v value to add to sales paid value.
	 */
	public void addToSalesPaidValue(double v){
		_salesPaidValue += v; 
	}

	/**
	 * Gets the partner's acquisitions array.
	 * @return an array of acquisitions.
	 */
	public Collection<Acquisition> getAcquisitions(){
		return _acquisitions;
	}

	/**
	 * Gets the partner's sales array.
	 * @return an array of sales.
	 */
	public Collection<Sale> getSales(){
		return _sales;
	}

	
	public void update(Notification not){
		_notifications.add(not);
	}

	public String notificationsToString(){
		String line = "";
		for(Notification iter: _notifications)
			line += "\n" + iter.toString();
		clearNotifications();
		if(line.length() == 1)
			line = "";
		return line;
	}

	public void clearNotifications(){
		_notifications.clear();
	}

	public String unitsToString(){
		return getName() + getUnits();
	}

	/**
	 * Gets the partner's information.
	 * @return the information of the partner as a String.
	 */
	public String toString(){
		return _id + "|" + _name + "|" + _address + "|" + _status + "|" + Math.round(_points) + "|" +
			Math.round(_acquisitionsValue) + "|" + Math.round(_salesBaseValue) + "|" + Math.round(_salesPaidValue) +
			notificationsToString();
	}
}
package ggc.core;

import java.io.Serializable;
import java.lang.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import ggc.core.BatchComparator;
import ggc.core.exception.UnavailableQuantityException;


/**
 * Class Product implements a Product.
 */
abstract public class Product implements Serializable{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202109192006L;

    /** double that contains information about product's highest price. */
    private double _maxPrice;

    private double _maxPriceEver;
     
    /** double that contains information about product's lowest price. */
    private double _minPrice;

    /** String that contains information about product's id. */
    private String _id;

    /** List of all batches of this product. */
    private ArrayList<Batch> _batches;

    /** int that contains information of the total quantity of units of this product*/
    private int _quantity;

    /** List of all batches of this product. */
    private Collection<InterestedEntity> _interestedEntities;
    /**
     * Default constructor: the _interestEntities array is
     * inicializated with every partner that already exists, 
     * but since any batches were made, then when initialized 
     * the batches' array is empty, _quantity and max price
     * and minimum price are equal to 0, because any batches 
     * were made yet because any units were made yet
     * and it is inicializated a String _id.
     */
    public Product(String id, ArrayList<Partner> partners) {
        _id = id;
        _maxPriceEver = 0;
        _maxPrice = 0;
        _minPrice = 0;
        _quantity = 0;
        _batches = new ArrayList<Batch>();
        _interestedEntities = new ArrayList<InterestedEntity>();
        for(Partner iter: partners)
            _interestedEntities.add(iter);
    }

    /**
     * 
     * 
     */
    public abstract String toString();

    public abstract int getN();

    public abstract int getRelation(Product p);

    public abstract Recipe getRecipe();

    /**
     * 
     * 
     */
    protected abstract boolean checkQuantity(int quantity, Partner p);

    /**
     * Gets the max price information.
     * @return the information of the highest price as a double.
     */
    public double getMaxPrice() {
        return _maxPrice;
    }

    public double getMinPrice() {
        return _minPrice;
    }

    public double getMaxPriceEver() {
        return _maxPriceEver;
    }

    /**
     * Gets the Product's id.
     * @return the id as a String.
     */
    public String getId() {
        return _id;
    }

    /**
     * Gets the array of all batches.
     * @return the array as a ArrayList.
     */
    public ArrayList<Batch> getBatches() {
        return _batches;
    }

    /**
     * Gets the Product quantity.
     * @return the quantity as a int.
     */
    public int getQuantity(){
        return _quantity;
    }

    /**
     * Add a value to quantity value
     * @param v value to add to quantity.
     */
    public void addQuantity(int i){
        _quantity = _quantity + i;
    }

    public void removeBatch(Batch batch){
        for(Batch b : _batches){
            if(batch.equals(b)){
                _batches.remove(b);
                break;
            }
        }
        this.updateMinPrice();
    }

    public void updateMinPrice(){
        double min;
        if(_batches.size() > 0){
            min = _batches.get(0).getPrice();
            for(Batch b : _batches){
                if(b.getPrice() < min)
                    min = b.getPrice();
            }
            _minPrice = min;
        }
    }

    public abstract BreakdownSale breakdown(Warehouse warehouse, int quantity, Partner part);

    public abstract boolean verifyAggregation(int difference) throws UnavailableQuantityException;

    public abstract int getMissingQuantity(int q);

    public abstract double aggregation(int difference, Warehouse w);

    public abstract double aggregationGetPrice(double v);

    /**
     * Register a batch.
     * Creates it and adds it to a the array of batches.
     * For more information @see Batch
     */
    public void registerBatch(double price, int q, Partner p, Product prod){
        if(_quantity == 0 && _maxPrice != 0){
            notifyInterestedEntity(new Notification("NEW", _id, price));
            _maxPrice = price;
            _minPrice = price;
        }   
        else if(price < _minPrice && _minPrice != 0){
            notifyInterestedEntity(new Notification("BARGAIN", _id, price));
            _minPrice = price;
        }    
        Batch b = new Batch(price, q, p, prod);
        _batches.add(b);
        Collections.sort(_batches, new BatchComparator());
        if(_maxPrice == 0){
            _maxPrice = price;
            _minPrice = price;
            _maxPriceEver = price;
        }
        else if(price < _minPrice && _minPrice != 0)
            _minPrice = price;
        else if(_maxPrice < price){
            _maxPrice = price;
            if(_maxPriceEver < price)
                _maxPriceEver = price;
        }
            
    }       

    public void addInterestedEntity(InterestedEntity partner){
        _interestedEntities.add(partner);
    }


    public boolean removeInterestedEntity(InterestedEntity partner){
        return _interestedEntities.remove(partner);
    }

    public void notifyInterestedEntity(Notification not){
        for(InterestedEntity iter: _interestedEntities)
            iter.update(not);
    }
}
package ggc.core;

import java.util.ArrayList;
import java.util.Collection;
import ggc.core.exception.UnavailableQuantityException;


public class SimpleProduct extends Product{

    public SimpleProduct(String id, ArrayList<Partner> partners) {
        super(id, partners);
    }

    @Override
    public Recipe getRecipe(){
        return null;
    }

    @Override
    public int getN(){
        return 3;
    }

    @Override
    public int getRelation(Product p){
        return 1;
    }

    @Override
    public int getMissingQuantity(int q){
        return q;
    }

    @Override
    public BreakdownSale breakdown(Warehouse warehouse, int quantity, Partner part){
        return null;
    }

    
    @Override 
    public boolean verifyAggregation(int difference) throws UnavailableQuantityException{
        throw new UnavailableQuantityException(this.getId(), this.getQuantity());
    }

    @Override
    public double aggregation(int difference, Warehouse w){
        return 0;
    }

    @Override
    public double aggregationGetPrice(double v){
        return v;
    }

    @Override
    protected boolean checkQuantity(int quantity, Partner p){
        int aux = 0;
        for(Batch i: super.getBatches())
            aux += i.getQuantity();
        if(aux >= quantity)
            return true;
        return false;
        //fazer excepções
    }
    @Override
    public String toString(){
        return this.getId() + "|" + Math.round(this.getMaxPrice()) + "|" + this.getQuantity();
    }
}

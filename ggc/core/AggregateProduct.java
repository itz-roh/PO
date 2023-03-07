package ggc.core;

import java.lang.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import ggc.core.exception.UnavailableQuantityException;


public class AggregateProduct extends Product{
    private Recipe _recipe;

    public AggregateProduct(String id, Recipe rec, ArrayList<Partner> partners) {
        super(id, partners);
        _recipe = rec;
    }

    @Override
    public int getN(){
        return 5;
    }

     @Override
    public int getRelation(Product p){
        if(this.equals(p))
            return 1;
        return this.getRecipe().getComponent(p).getQuantity();
    }

    @Override
    public int getMissingQuantity(int q){
        return q - this.getQuantity();
    }


    @Override
    public BreakdownSale breakdown(Warehouse warehouse, int quantity, Partner part){
        int id = warehouse.getTransactionId();
        double newPriceComponents;
        double value = 0;

        /**Add batches*/
            for(Component c: this.getRecipe().getComponents()){
                if(c.getProduct().getBatches().size() != 0){
                    newPriceComponents = c.getProduct().getBatches().get(0).getPrice(); 
                    for(Batch b: c.getProduct().getBatches()){
                        if(b.getPrice() < newPriceComponents)
                            newPriceComponents = b.getPrice();
                    }
                }
                else
                    newPriceComponents = this.getMaxPrice();
                c.getProduct().registerBatch(newPriceComponents, c.getQuantity() * quantity, part, c.getProduct());
            }

            /**Decrease quantity and delete if needed*/
            value = warehouse.decreaseBatches(quantity,this);

            /**create transaction*/
            warehouse.addTransactionId();
            return new BreakdownSale(this, quantity, part, value, id, warehouse.getDate());

    }


    @Override
    public boolean verifyAggregation(int difference) throws UnavailableQuantityException{
        boolean teste = true;
        for(Component c : this.getRecipe().getComponents()){
            if(c.getProduct().getQuantity() < difference * c.getQuantity())
                try{
                    teste = teste && c.getProduct().verifyAggregation(c.getProduct().getQuantity() - difference * c.getQuantity());
                }catch(UnavailableQuantityException uqe){
                    throw new UnavailableQuantityException(uqe.getProductId(), c.getQuantity(), uqe.getStock());
                }
            else
                teste = teste && true;
        }
        return teste;    
    }

    @Override
    public double aggregation(int difference, Warehouse w){
        double value = 0;
        for(Component c : this.getRecipe().getComponents()){
            value = value +  w.decreaseBatches(difference * c.getQuantity(), c.getProduct());
            c.getProduct().aggregation(c.getProduct().getQuantity() - difference * c.getQuantity(), w);
        }
        return value;
    }

    @Override
    public double aggregationGetPrice(double v){
        return v * (1 + this.getRecipe().getMultiplierValue());
    }


    @Override
    protected boolean checkQuantity(int quantity, Partner p){
        int aux = 0;
        for(Batch i: super.getBatches())
            if(i.getPartner().equals(p))
                aux += i.getQuantity();
        if(aux >= quantity)
            return true;
        return false;
    }

    public Recipe getRecipe(){
        return _recipe;
    }
    @Override
    public String toString(){
        String line = this.getId() + "|" + Math.round(this.getMaxPriceEver()) + "|" + this.getQuantity() + "|";
        for (Component c : _recipe.getComponents()){
            line = line + c.getProduct().getId()+ ":" +c.getQuantity() + "#";
        }
        line = line.substring(0, line.length()-1);
        return line;
    }

}

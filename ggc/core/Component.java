package ggc.core;

import java.io.Serializable;

public class Component implements Serializable{
    private static final long serialVersionUID = 202109192006L;
    private Product _product;
    private int _quantity;
    
    public Component(Product p, int q){
        _product = p;
        _quantity = q;
    }
    public int getQuantity(){
        return _quantity;
    }

    public Product getProduct(){
        return _product;
    }
}

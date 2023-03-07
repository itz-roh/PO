package ggc.core;

import java.io.Serializable;
import java.util.Comparator;

public class ProductComparator implements Comparator<Product>, Serializable{
    private static final long serialVersionUID = 202109192006L;
    @Override
    public int compare(Product a, Product b){
        return a.getId().compareToIgnoreCase(b.getId());
    }
}
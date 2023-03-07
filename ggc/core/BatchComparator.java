package ggc.core;

import java.io.Serializable;
import java.util.Comparator;

public class BatchComparator implements Comparator<Batch>, Serializable{
    private static final long serialVersionUID = 202109192006L;
    @Override
    public int compare(Batch a, Batch b){
       if(a.getProduct().getId().equals(b.getProduct().getId())){
        if(a.getPartner().getId().equals(b.getPartner().getId())){
            if(a.getPrice() > b.getPrice())
                return 1;
            else if(a.getPrice() < b.getPrice())
                return -1;
            return a.getQuantity() - b.getQuantity();
        }
        return a.getPartner().getId().compareTo(b.getPartner().getId());
       }
       return a.getProduct().getId().compareTo(b.getProduct().getId());
    }   
}
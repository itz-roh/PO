package ggc.core;

import java.io.Serializable;
import java.util.Comparator;

public class PartnersComparator implements Comparator<Partner>, Serializable{
    private static final long serialVersionUID = 202109192006L;
    @Override
    public int compare(Partner a, Partner b){
        return a.getId().compareToIgnoreCase(b.getId());
    }
}

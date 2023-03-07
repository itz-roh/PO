package ggc.core;

import java.io.Serializable;
import ggc.core.exception.BadEntryException;

public class Date implements Serializable{
    private static final long serialVersionUID = 202109192006L;
    private int _days;
    
    public Date(int d){
        _days = d;
    }

    public Date(){
        _days = 0;
    }

    public void addToNow(int days) throws BadEntryException{
        if(days <= 0)
            throw new BadEntryException("Valor da Data invalido");
        _days += days;
    }

    public int difference(int d) {
        return d - _days;
    }

    public int getDays(){
        return _days;
    }

    public void setDays(int n){
        _days = n;
    }
}
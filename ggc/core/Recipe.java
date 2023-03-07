package ggc.core;

import java.util.Collection;
import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private static final long serialVersionUID = 202109192006L;
    private Collection<Component> _components;
    private final double _multiplierValue;
    
    public Recipe(Collection<Component> components, double alpha){
        _components = components;
        _multiplierValue = alpha;
    }

    public Collection<Component> getComponents(){
        return _components;
    }

    public Component getComponent(Product p){
        for(Component c : _components){
            if(c.getProduct().equals(p))
                return c;
        }
        return null;
    }

    public double getMultiplierValue(){
        return _multiplierValue;
    }
}


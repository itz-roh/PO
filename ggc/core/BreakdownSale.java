package ggc.core;

import java.util.Collection;
import java.util.ArrayList;

public class BreakdownSale extends Sale{
	private double _diference;

	public BreakdownSale(Product p, int quantity, Partner part, double value, int id ,Date now){
		super(p,quantity,part,value, id, now);
		_diference = 0;
	}

	public double getDiference(){
		return _diference;
	}

	public void calculateDiference(){
		double componentsPrice = 0;
		double aggregatePrice = 0;
		double lowest;

		/**if (batches.lenght() == 0) ???*/
		for(Component c :this.getProduct().getRecipe().getComponents()){
			if(c.getProduct().getBatches().size() > 0){
				lowest = c.getProduct().getBatches().get(0).getPrice();
				for(Batch b : c.getProduct().getBatches()){
					if (b.getPrice()<lowest)
						lowest = b.getPrice();
				}
			}
			else
				lowest = c.getProduct().getMaxPriceEver();
			componentsPrice = componentsPrice + lowest * (c.getQuantity() * this.getQuantity());
		}

		aggregatePrice = this.getValue();
		_diference = aggregatePrice - componentsPrice;
		this.setValue(_diference);
		this.setValue(this.doPointsInTime(0,true));
	}

	@Override
	public String toString(){
		String line;
		if(_diference > 0)
			line = "DESAGREGAÇÃO|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|" + Math.round(_diference) + "|"+ Math.round(this.getValue()) + "|" + this.getPaymentDate().getDays() + "|";
		else
			line = "DESAGREGAÇÃO|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|" + 0 + "|"+ Math.round(this.getValue()) + "|" + this.getPaymentDate().getDays() + "|";
		for (Component c : this.getProduct().getRecipe().getComponents()){
            line = line + c.getProduct().getId()+ ":" +c.getQuantity() * this.getQuantity() + ":" + Math.round(c.getProduct().getMinPrice() * (c.getQuantity() * this.getQuantity())) + "#";
        }
         line = line.substring(0, line.length()-1);
        return line;
	}
}
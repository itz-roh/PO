package ggc.core;

public abstract class Sale extends Transaction{
	private Partner _partner;

	public Sale(Product p, int quantity, Partner part, double value, int id, Date now){
		super(id, p,value,quantity,now);
		_partner = part;
	}

	public Sale(Product p, int quantity, Partner part, double value, int id){
		super(id, p,value,quantity);
		_partner = part;
	}

	public Partner getPartner(){
		return _partner;
	}

	public Product getProduct(){
		return super.getProduct();
	}

	double doPointsInTime(boolean isPaid){ 
		double newValue = super.getValue() * 0.90;
		if(isPaid)
			_partner.setPoints(_partner.getPoints() + (newValue * 10));
		return newValue;
	}

	double doPointsInTime(int days, boolean isPaid){
		double newValue = super.getValue();
		if(_partner.getStatus().equals("NORMAL") && isPaid)
			_partner.setPoints(_partner.getPoints() + (10 * super.getValue()));

		else if(_partner.getStatus().equals("SELECTION")){
			newValue = super.getValue();
			if (days < 2)
				newValue = newValue * 0.95;
			if(isPaid)
				_partner.setPoints(_partner.getPoints() + (newValue * 10));
		}
		else if(_partner.getStatus().equals("ELITE")){
			newValue = super.getValue() * 0.90;
			if(isPaid)
				_partner.setPoints(_partner.getPoints() + (newValue * 10));
		}
		return newValue;
	}

	double removePoints1(int days, boolean isPaid){
		double newValue = super.getValue();
		if(_partner.getStatus().equals("NORMAL")){
			newValue = super.getValue() * (1 + (0.05 * days));
			if(isPaid)
				_partner.setPoints(0);
		}
		else if(_partner.getStatus().equals("SELECTION")){
			newValue = super.getValue() * (1 + (0.02 * days));
			if (days > 2 && isPaid){
				_partner.setPoints(_partner.getPoints() * 0.1);
				_partner.setStatus("NORMAL");
			}
		}
		else if(_partner.getStatus().equals("ELITE")){
			newValue = super.getValue() * 0.95;
			if (days > 15 && isPaid){
				_partner.setPoints(_partner.getPoints() * 0.25);
				_partner.setStatus("SELECTION");
			}
		}
		return newValue;
	}

	double removePoints2(int days, boolean isPaid){
		double newValue = super.getValue();
		if(_partner.getStatus().equals("NORMAL")){
			newValue = super.getValue() * (1 + (0.10 * days));
			if(isPaid)
				_partner.setPoints(0);
		}
		else if(_partner.getStatus().equals("SELECTION")){
			newValue = super.getValue() * (1 + (0.05 * days));
			if (days > 2 && isPaid){
				_partner.setPoints(_partner.getPoints() * 0.1);
				_partner.setStatus("NORMAL");
			}
		}
		else if(_partner.getStatus().equals("ELITE")){
			if (days > 15 && isPaid){
				_partner.setPoints(_partner.getPoints() * 0.25);
				_partner.setStatus("SELECTION");
			}
		}
		return newValue;
	}

	abstract public String toString();
}


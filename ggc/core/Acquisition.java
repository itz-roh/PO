package ggc.core;

public class Acquisition extends Transaction{
	private Partner _partner;

	public Acquisition(Product prod, int quantity, Partner part, double value, int id, Date now){
		super(id, prod, value,quantity, now);
		_partner = part;
	}

	public Partner getPartner(){
		return _partner;
	}

	@Override
	public String toString(){
		return "COMPRA|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|" + Math.round(this.getValue()) + "|" + this.getPaymentDate().getDays();
	}
}
package ggc.core;

import java.io.Serializable;

public class SaleByCredit extends Sale{
	private Date _deadline;
	private double _amountPaid;
	private int _n;

	public SaleByCredit(Product p, int quantity, Partner part, double value, int id, int deadline){
		super(p,quantity,part,value, id);
		_n = p.getN();
		_deadline = new Date(deadline);
		_amountPaid = 0;
	}

	public boolean isPaid(){
		return (this.getPaymentDate() != null);
	} 

	public double getAmountPaid(){
		return _amountPaid;
	}
	
	void getPaid(Date now){
		int diference = _deadline.difference(now.getDays());
		if ( -diference >= _n){
			_amountPaid = super.doPointsInTime(this.isPaid());
		}
		else if (0 <= -diference && -diference < _n){
			_amountPaid = super.doPointsInTime(-diference, this.isPaid());
		}
		else if (0 < diference && diference <= _n){
			_amountPaid = super.removePoints1(diference, this.isPaid());
		}
		else if ( diference > _n){
			_amountPaid = super.removePoints2(diference, this.isPaid());
		}
	}

	public String getPartnerId(){
		return super.getPartner().getId();
	}

	public String getProductId(){
		return super.getProduct().getId();
	}

	@Override
	public String toString(){
		if(this.getPaymentDate() != null)
			return "VENDA|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|" + Math.round(this.getValue()) + "|" + Math.round(_amountPaid) + "|" + _deadline.getDays() + "|" + this.getPaymentDate().getDays();
		return "VENDA|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|" + Math.round(this.getValue()) + "|" + Math.round(_amountPaid) + "|" + _deadline.getDays();
	}
}
	

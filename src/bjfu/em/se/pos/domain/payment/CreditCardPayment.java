package bjfu.em.se.pos.domain.payment;

public class CreditCardPayment extends Payment {
	public CreditCardPayment(int amount) {
		super(amount,PaymentType.ByCreditCard);
	}
}

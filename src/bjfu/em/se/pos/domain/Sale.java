package bjfu.em.se.pos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import bjfu.em.se.pos.domain.payment.CashPayment;
import bjfu.em.se.pos.domain.payment.CheckPayment;
import bjfu.em.se.pos.domain.payment.CreditCardPayment;
import bjfu.em.se.pos.domain.payment.Payment;
import bjfu.em.se.pos.domain.payment.PaymentType;

/**
 * 销售类
 * 一个Sale对象对应现实中的一次购物
 * @author Roy
 *
 */
public class Sale {
	private long id;
	private boolean isComplete=false;
	private List<SalesLineItem> lineItems;
	private Payment payment=null;
	private Date date;
	public Sale() {
		lineItems=new ArrayList<SalesLineItem> ();
		date = new Date();
		isComplete=false;
		id=-1;
	}

	public Sale(long id, List<SalesLineItem> lineItems, Payment payment, Date date) {
		this.id = id;
		this.lineItems = lineItems;
		this.payment = payment;
		this.date = date;
		this.isComplete=true;
	}

	/**
	 * 输入新的购买商品
	 * @param desc 商品信息
	 * @param qty 购买数量
	 */
	public void makeLineItem(ProductDescription desc, int qty) {
		SalesLineItem sl=new SalesLineItem(desc,qty);

		lineItems.add(sl);
	}
	
	/**
	 * 标记商品输入结束
	 */
	public void becomeComplete() {
		isComplete=true;
	}
	public int getTotal() {
		int total=0;
		//JavaSE 5引入的集合遍历语法
		for (SalesLineItem item:lineItems){
			total+=item.getSubTotal();
		}
		return total;
	}
	public int makePayment(int amount, PaymentType type) {
		int total=getTotal();
		switch(type) {
		case ByCash:
			payment=new CashPayment(total);
			break;
		case ByCreditCard:
			payment=new CreditCardPayment(total);
			break;
		case ByCheck:
			payment=new CheckPayment(total);
			break;
		}
		return amount-total;
	}
	public List<SalesLineItem> getLineItems() {
		return Collections.unmodifiableList(lineItems);
	}
	public Payment getPayment() {
		return payment;
	}
	/**
	 * 计算找零
	 * @return
	 */
	public int getBalance() {
		return payment.getAmount()-getTotal();
	}
	public Date getDate() {
		return date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}

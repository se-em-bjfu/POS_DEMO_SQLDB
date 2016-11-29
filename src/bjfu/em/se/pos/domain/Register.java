package bjfu.em.se.pos.domain;

import java.io.IOException;
import java.sql.SQLException;

import bjfu.em.se.pos.domain.payment.PaymentType;
import org.hsqldb.cmdline.SqlToolError;

/***
 * 收款台类
 * @author Roy
 *
 */
public class Register {
	private Sale currentSale=null;
	private ProductCatalog productCatalog;
	private Store store;
	private String name;
	private String id;
	public Register(String id, String name, Store store, ProductCatalog productCatalog) {
		this.id=id;
		this.name=name;
		this.store=store;
		this.productCatalog=productCatalog;
	}
	/**
	 * 开始新销售
	 */
	public void startNewSale(){
		currentSale=new Sale();		
	}
	/**
	 * 输入购买的商品
	 * @param itemId 商品条码
	 * @param qty 商品数量
	 * @return 所购买商品的信息
	 * @throws ProductNotFoundException 
	 * @throws SQLException 
	 */
	public ProductDescription enterItem(String itemId,int qty) throws ProductNotFoundException, SQLException{
		ProductDescription desc=productCatalog.getProduct(Long.parseLong(itemId));
		if (desc==null) {
			throw new ProductNotFoundException(itemId);
		}
		currentSale.makeLineItem(desc,qty);
		return desc;
	}
	/**
	 * 结束商品输入
	 * @return 商品总金额(单位为分)
	 */
	public int endSale(){
		currentSale.becomeComplete();
		return currentSale.getTotal();
	}
	/**
	 * 支付
	 * @param amount 
	 * @param type
	 * @return 找零金额(单位为分)
	 */
	public int makePayment(int amount, PaymentType type) throws SqlToolError, SQLException, IOException {
		int balance=currentSale.makePayment(amount,type);
		store.addSale(currentSale);
		return balance;
	}
	public Sale getSale() {
		return currentSale;
	}
	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}
}

package bjfu.em.se.pos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 超市类
 * @author Roy
 *
 */
public class Store {
	private String name;
	private List<Sale> completedSales;
	
	public Store(String name) {
		this.name=name;
		completedSales=new ArrayList<Sale>();
	}
	
	/**
	 * 添加已处理完毕的销售
	 * @param sale 
	 */
	public void addSale(Sale sale){
		completedSales.add(sale);
	}
	
	/**
	 * 返回已处理的销售列表
	 * @return
	 */
	public List<Sale> getSales() {
		return Collections.unmodifiableList(completedSales);
	}
}

package bjfu.em.se.pos.domain;

import bjfu.em.se.pos.persistence.PersistManager;
import bjfu.em.se.pos.persistence.SalePersistor;
import org.hsqldb.cmdline.SqlToolError;

import java.io.IOException;
import java.sql.SQLException;
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

	public Store(String name) {
		this.name=name;
	}
	
	/**
	 * 添加已处理完毕的销售
	 * @param sale 
	 */
	public void addSale(Sale sale) throws SqlToolError, SQLException, IOException {
		PersistManager persistManager = PersistManager.getInstance();
		persistManager.getSalePersistor().create(sale);
	}
	
	/**
	 * 返回已处理的销售列表
	 * @return
	 */
	public List<Sale> getSales() throws SQLException, IOException, SqlToolError {
		PersistManager persistManager = PersistManager.getInstance();
		return persistManager.getSalePersistor().listSales();
	}
}

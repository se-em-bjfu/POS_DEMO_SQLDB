package bjfu.em.se.pos.domain;

import java.sql.SQLException;
import java.util.List;

import bjfu.em.se.pos.persistence.PersistManager;
import bjfu.em.se.pos.persistence.ProductDescriptionPersistor;

/**
 * 超市商品目录
 * @author Roy
 *
 */
public class ProductCatalog {
	private ProductDescriptionPersistor persistor;
	public ProductCatalog() throws Exception {
		persistor=PersistManager
				.getInstance()
				.getProductDescriptionPersistor();
	}
	
	/**
	 * 查找某id对应的商品
	 * @param id 商品id
	 * @return 对应的商品信息
	 * @throws SQLException 
	 */
	public ProductDescription getProduct(long id) throws SQLException {
		return persistor.retrieve(id);
	}
	
	/**
	 * 向产品目录中添加商品
	 * @param id 商品id
	 * @param name 商品名称 
	 * @param description 商品描述
	 * @param price 商品单价
	 * @throws SQLException 
	 */
	public void addProductDescription(long id, String name,String description ,int price) throws SQLException{
		ProductDescription desc=new ProductDescription(id,name,description,price);
		persistor.create(desc);
	}

	public List<ProductDescription> listDescriptions() throws SQLException {
		return persistor.listDescriptions();
	}
}

package bjfu.em.se.pos.domain;

/**
 * 商品信息
 * @author Roy
 *
 */
public class ProductDescription {
	private long id;
	private String name;
	private String description;
	private int price;
	public ProductDescription(long id, String name, String description,
			int price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getPrice() {
		return price;
	}
	
}

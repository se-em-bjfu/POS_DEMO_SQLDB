package bjfu.em.se.pos.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bjfu.em.se.pos.domain.ProductDescription;

public class ProductDescriptionPersistor {
	private PersistManager persistManager;
	
	ProductDescriptionPersistor(PersistManager persistManager) {
		this.persistManager=persistManager;
	}
	
	public List<ProductDescription> listDescriptions() throws SQLException {
		Connection conn=persistManager.getConnection();
		Statement stat=null;
		ArrayList<ProductDescription> descriptions=new ArrayList<ProductDescription>();
		try {
			stat=conn.createStatement();
			ResultSet rs=stat.executeQuery("select * from productDescription order by id asc");
			while (rs.next()) {
				ProductDescription desc=new
						ProductDescription(
								rs.getLong("id"),
								rs.getString("name").trim(),
								rs.getString("description").trim(),
								rs.getInt("price")
						);
				descriptions.add(desc);
			}
			return descriptions;
		} finally {
			if (stat!=null) 
				stat.close();
			conn.close();
		}
	}
	public ProductDescription retrieve(long id) throws SQLException {
		Connection conn=persistManager.getConnection();
		PreparedStatement stat=null;

		try {
			stat=conn.prepareStatement("select * from productDescription where id = ?");
			stat.setLong(1, id);
			ResultSet rs=stat.executeQuery();
			if (rs.next()) {
				ProductDescription desc=new
						ProductDescription(
								rs.getLong("id"),
								rs.getString("name").trim(),
								rs.getString("description").trim(),
								rs.getInt("price")
						);
				return desc;
			}
			return null;
		}finally {
			if (stat!=null) {
				stat.close();
			}
			conn.close();
		}
	}
	public void create(ProductDescription desc) throws SQLException {
		Connection conn=persistManager.getConnection();
		PreparedStatement stat=null;
		try {
			stat=conn.prepareStatement("insert into productDescription(name,description,price)"
					+ " values (?,?,?)");
			stat.setString(1, desc.getName());
			stat.setString(2, desc.getDescription());
			stat.setInt(3, desc.getPrice());
			stat.execute();
		} finally {
			if (stat!=null) 
				stat.close();
			conn.close();
		}
	}
}

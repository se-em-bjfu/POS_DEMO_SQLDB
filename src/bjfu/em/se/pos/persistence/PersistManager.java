package bjfu.em.se.pos.persistence;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;


public class PersistManager {
	private ProductDescriptionPersistor productDescriptionPersistor=null;
	private SalePersistor salePersistor=null;

	private static PersistManager manager=null;
	
	private PersistManager() {
		try {
			initDatabase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void initDatabase() throws SQLException, IOException, SqlToolError {
		Connection conn=null;
		try {
			conn=getConnection();
			SqlFile sqlFile = new SqlFile(
					new File(PersistManager.class.getResource("/init_script.sql").getFile()),
					"UTF-8");
			sqlFile.setConnection(conn);
			sqlFile.execute();
		} finally {
			conn.close();
		}
	}

	public Connection getConnection() throws SQLException {
		Connection connection=DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "sa", "");
		return connection;
	}

	public ProductDescriptionPersistor getProductDescriptionPersistor() {
		if (productDescriptionPersistor==null) {
			productDescriptionPersistor=new ProductDescriptionPersistor(this);
		}
		return productDescriptionPersistor;
	}

	public SalePersistor getSalePersistor() {
		if (salePersistor==null) {
			salePersistor=new SalePersistor(this,getProductDescriptionPersistor()) ;
		}
		return salePersistor;
	}
	
	public static PersistManager getInstance() throws SqlToolError, SQLException, IOException {
		if (manager==null) {
			manager=new PersistManager();
		}
		return manager;
	}
}

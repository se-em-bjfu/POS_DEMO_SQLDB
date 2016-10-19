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
	private Connection conn=null;
	
	private static PersistManager manager=null;
	
	private PersistManager() throws SQLException,IOException, SqlToolError {
		conn=DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "sa", "");
		SqlFile sqlFile=new SqlFile(
				new File(PersistManager.class.getResource("/init_script.sql").getFile()),
				"UTF-8");
		sqlFile.setConnection(conn);
		sqlFile.execute();	
	}
	
	public ProductDescriptionPersistor getProductDescriptionPersistor() {
		if (productDescriptionPersistor==null) {
			productDescriptionPersistor=new ProductDescriptionPersistor(conn);
		}
		return productDescriptionPersistor;
	}
	
	public static PersistManager getInstance() throws Exception {
		if (manager==null) {
			manager=new PersistManager();
		}
		return manager;
	}
}

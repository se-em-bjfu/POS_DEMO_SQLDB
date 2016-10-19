package bjfu.em.se.pos.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bjfu.em.se.pos.domain.ProductCatalog;
import bjfu.em.se.pos.ui.model.ProductListTableModel;

public class ProductListDialog extends JDialog {
	private JTable tbProductList;
	public ProductListDialog(ProductCatalog catalog) throws SQLException {
		setSize(600,500);
		setLocation(100,100);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("商品列表");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tbProductList = new JTable();
		tbProductList.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		tbProductList.setModel(new ProductListTableModel(catalog.listDescriptions()));
		scrollPane.setViewportView(tbProductList);
	}

	public static void showProductList(ProductCatalog catalog) throws SQLException {
		ProductListDialog dialog=new ProductListDialog(catalog);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}

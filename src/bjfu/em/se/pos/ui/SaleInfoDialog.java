package bjfu.em.se.pos.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bjfu.em.se.pos.domain.Sale;
import bjfu.em.se.pos.ui.model.SaleInfoTableModel;

public class SaleInfoDialog extends JDialog {
	private JTable tbLineItems;
	private JLabel lblSaleInfo;
	public SaleInfoDialog(Sale sale) {
		setSize(600,500);
		setLocation(100,100);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("销售信息");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		lblSaleInfo = new JLabel(
				String.format(
				"共%d种商品 合计%.2f元",
				sale.getLineItems().size(),
				(double)sale.getTotal()/100)
			);
		lblSaleInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		getContentPane().add(lblSaleInfo, BorderLayout.NORTH);
		
		JScrollPane scrollPane=new JScrollPane(); 
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tbLineItems = new JTable();
		tbLineItems.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		tbLineItems.setModel(new SaleInfoTableModel(sale));
		scrollPane.setViewportView(tbLineItems);
		
	}
	
	public static void showDialog(Sale sale) {
		SaleInfoDialog dialog=new SaleInfoDialog(sale);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
}

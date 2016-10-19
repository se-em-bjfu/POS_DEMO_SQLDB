package bjfu.em.se.pos.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bjfu.em.se.pos.domain.Sale;
import bjfu.em.se.pos.ui.model.SaleListTableModel;

public class SaleListDialog extends JDialog {
	private JTable tbSales;
	private List<Sale> sales;
	public SaleListDialog(List<Sale> sales) {
		this.sales=sales;
		initUI();
	}

	private void initUI() {
		setTitle("销售信息列表");
		setSize(500,600);
		setLocation(100,100);
		
		JLabel lblInfo = new JLabel(
			String.format("共有%d笔销售信息",sales.size()));
		lblInfo.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		getContentPane().add(lblInfo, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tbSales = new JTable();
		tbSales.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		tbSales.setModel(new SaleListTableModel(sales));
		tbSales.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()<=1)
					return;
				showSaleInfo(e);
			}
			
		});
		scrollPane.setViewportView(tbSales);
	}
	
	private void showSaleInfo(MouseEvent e){
		int row=tbSales.rowAtPoint(e.getPoint());
		SaleInfoDialog.showDialog(
			SaleListDialog.this.sales.get(row));		
	}

	public static void showDialog(List<Sale> sales) {
		SaleListDialog dialog=new SaleListDialog(sales);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setVisible(true);		
	}

}

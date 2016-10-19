package bjfu.em.se.pos.ui.model;

import javax.swing.table.AbstractTableModel;

import bjfu.em.se.pos.domain.Sale;
import bjfu.em.se.pos.domain.SalesLineItem;

public class SaleInfoTableModel extends AbstractTableModel {
	private Sale sale;

	public SaleInfoTableModel(Sale sale) {
		super();
		this.sale = sale;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		if (sale==null)
			return 0;
		return sale.getLineItems().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		SalesLineItem item=sale.getLineItems().get(rowIndex);
		switch(colIndex) {
		case 0:
			return rowIndex+1;
		case 1:
			return item.getProductDescription().getName();
		case 2:
			return String.format(
				"%.2f元",(double)item.getProductDescription().getPrice()/100);
		case 3:
			return item.getQuantity();
		case 4:
			return item.getProductDescription().getDescription(); 
		}
		return "";
	}

	@Override
	public String getColumnName(int colIndex) {
		switch(colIndex) {
		case 0:
			return "序号";
		case 1:
			return "商品名称";
		case 2:
			return "商品单价";
		case 3:
			return "商品数量";
		case 4:
			return "商品描述"; 
		}
		return "";
	}
	
	
	
	
	

}

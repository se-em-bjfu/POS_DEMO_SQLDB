package bjfu.em.se.pos.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import bjfu.em.se.pos.domain.ProductDescription;

public class ProductListTableModel extends AbstractTableModel {
	List<ProductDescription> descriptions;
	
	
	public ProductListTableModel(List<ProductDescription> descriptions) {
		super();
		this.descriptions = descriptions;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return descriptions.size();
	}

	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "ID";
		case 1:
			return "名称";
		case 2:
			return "描述";
		case 3:
			return "价格(元)";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductDescription desc=descriptions.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return desc.getId();
		case 1:
			return desc.getName();
		case 2:
			return desc.getDescription();
		case 3:
			return String.format("%d.%d", desc.getPrice()/100, desc.getPrice()%100 );
		default:
			return "";
		}
	}

}

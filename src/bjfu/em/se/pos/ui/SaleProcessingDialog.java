package bjfu.em.se.pos.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bjfu.em.se.pos.domain.ProductDescription;
import bjfu.em.se.pos.domain.ProductNotFoundException;
import bjfu.em.se.pos.domain.Register;

public class SaleProcessingDialog extends JDialog{
	private Register register;
	private JTextField txtItemId;
	private JTextField txtItemQuantity;
	private JLabel lblProductInfo;
	public SaleProcessingDialog(Register register) {
		this.register=register;
		initUI();
	}
	
	private void initUI(){
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(450,300);
		this.setLocation(100, 100);
		
		setTitle("销售处理");
		getContentPane().setLayout(null);
		
		lblProductInfo = new JLabel("");
		lblProductInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductInfo.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblProductInfo.setBounds(14, 13, 404, 58);
		getContentPane().add(lblProductInfo);
		
		JLabel lblid = new JLabel("商品ID");
		lblid.setFont(new Font("宋体", Font.PLAIN, 16));
		lblid.setBounds(14, 78, 72, 18);
		getContentPane().add(lblid);
		
		txtItemId = new JTextField();
		txtItemId.setBounds(80, 76, 218, 24);
		getContentPane().add(txtItemId);
		txtItemId.setColumns(10);
		
		JLabel label_2 = new JLabel("数量");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(14, 104, 72, 18);
		getContentPane().add(label_2);
		
		txtItemQuantity = new JTextField();
		txtItemQuantity.setColumns(10);
		txtItemQuantity.setBounds(80, 102, 218, 24);
		txtItemQuantity.setText("1");
		getContentPane().add(txtItemQuantity);
		
		JButton btnEnterItem = new JButton("输入商品");
		btnEnterItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterItem();
			}
		});
		btnEnterItem.setBounds(57, 135, 113, 27);
		getContentPane().add(btnEnterItem);
		
		JButton btnEndSale = new JButton("完成输入");
		btnEndSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endSale();
			}
		});
		btnEndSale.setBounds(273, 135, 113, 27);
		getContentPane().add(btnEndSale);
		
		JButton btnSaleInfo = new JButton("查看已输入商品");
		btnSaleInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSaleInfo();
			}
		});
		btnSaleInfo.setBounds(144, 194, 146, 27);
		getContentPane().add(btnSaleInfo);
		
		JButton btnProductList = new JButton("商品列表");
		btnProductList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ProductListDialog.showProductList(register.getProductCatalog());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnProductList.setBounds(305, 75, 93, 27);
		getContentPane().add(btnProductList);
	}
	
	private void enterItem(){
		try {
			int quantity=Integer.parseInt(txtItemQuantity.getText());
			ProductDescription desc;
			desc = register.enterItem(txtItemId.getText(), quantity);
			lblProductInfo.setText(
				String.format("%s %.2f * %d = %.2f",
					desc.getName(),(double)desc.getPrice()/100,
					quantity,(double)(quantity*desc.getPrice())/100
			));
				
			txtItemId.setText("");
			txtItemQuantity.setText("1");
		} catch (ProductNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch(NumberFormatException exp) {
			JOptionPane.showMessageDialog(this, "未输入正确的商品数量");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}
	
	private void endSale(){
		int amount=register.endSale();
		dispatchEvent(new WindowEvent(
				this, WindowEvent.WINDOW_CLOSING
		));		
		PaymentProcessingDialog dialog=new PaymentProcessingDialog(register,amount);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	private void showSaleInfo(){
		SaleInfoDialog.showDialog(register.getSale());
	}

	public static void showDialog(Register register) {
		SaleProcessingDialog dialog=new SaleProcessingDialog(register);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}

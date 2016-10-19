package bjfu.em.se.pos.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import bjfu.em.se.pos.domain.ProductCatalog;
import bjfu.em.se.pos.domain.Register;
import bjfu.em.se.pos.domain.Store;


public class MainFrame extends JFrame {
	private Register register;
	private Store store;
	public MainFrame(Register register,Store store) {
		this.register = register;
		this.store=store;
		initUI();
	}
	
	private void initUI() {
		setBounds(40,40,500,350);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("超市POS系统");
		getContentPane().setLayout(null);
		
		JButton btnProcessSale = new JButton("处理新销售");
		btnProcessSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				register.startNewSale();
				SaleProcessingDialog.showDialog(register);
			}
		});
		btnProcessSale.setBounds(61, 62, 150, 83);
		getContentPane().add(btnProcessSale);
		
		JButton btnShowSales = new JButton("察看销售记录");
		btnShowSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaleListDialog.showDialog(store.getSales());
			}
		});
		btnShowSales.setBounds(277, 62, 173, 83);
		getContentPane().add(btnShowSales);
		
		JButton btnExit = new JButton("退出程序");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		btnExit.setBounds(367, 277, 113, 27);
		getContentPane().add(btnExit);		
		
		JButton btnShowProducts = new JButton("察看商品列表");
		btnShowProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ProductListDialog.showProductList(register.getProductCatalog());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnShowProducts.setBounds(14, 277, 164, 27);
		getContentPane().add(btnShowProducts);
	}
	
	private void closeWindow(){
		dispatchEvent(new WindowEvent(
			this,WindowEvent.WINDOW_CLOSING));
	}

	public static void main(String[] args) throws Exception {
		Store store=new Store("学院路美廉美");
		ProductCatalog productCatalog=new ProductCatalog();
		/*
		productCatalog.addProductDescription("1", "面包", "好吃的面包", 400);
		productCatalog.addProductDescription("2", "香肠", "好吃的香肠", 350);
		productCatalog.addProductDescription("3", "榨菜", "好吃的榨菜", 050);
		productCatalog.addProductDescription("4", "方便面", "好吃的方便面", 300);
		productCatalog.addProductDescription("5", "可乐", "好喝的可乐", 200);
		productCatalog.addProductDescription("6", "饼干", "好吃的饼干", 430);
		productCatalog.addProductDescription("7", "蛋糕", "好吃的蛋糕", 680);
		productCatalog.addProductDescription("8", "牛奶", "盒装牛奶", 530);
		productCatalog.addProductDescription("9", "煮鸡蛋", "好吃的鸡蛋", 150);
		productCatalog.addProductDescription("10", "鸡腿", "好吃的鸡腿", 1200);
		*/
		
		Register register=new Register("1","1号收银台",store,productCatalog);
		
		try {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "无法设置Windows外观");;
		}
		MainFrame frame=new MainFrame(register,store);
		frame.setVisible(true);
	}
}

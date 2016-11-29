package bjfu.em.se.pos.persistence;

import bjfu.em.se.pos.domain.Sale;
import bjfu.em.se.pos.domain.SalesLineItem;
import bjfu.em.se.pos.domain.payment.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roy on 2016/11/28.
 */
public class SalePersistor {
    private PersistManager persistManager;
    private ProductDescriptionPersistor productDescriptionPersistor;

    public SalePersistor(PersistManager persistManager, ProductDescriptionPersistor productDescriptionPersistor) {
        this.persistManager = persistManager;
        this.productDescriptionPersistor = productDescriptionPersistor;
    }

    public long create(Sale sale) throws SQLException {
        Connection conn=persistManager.getConnection();
        //启动Transaction
        conn.setAutoCommit(false);
        conn.commit();

        PreparedStatement stat=null;
        PreparedStatement stat2=null;
        try {
            //插入Sale记录
            stat=conn.prepareStatement("insert into sale(saleTime,payAmount,paymentType)"
                    + " values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stat.setTimestamp(1,new Timestamp(sale.getDate().getTime()));
            stat.setInt(2, sale.getPayment().getAmount());
            stat.setString(3, sale.getPayment().getType().toString());
            stat.executeUpdate();
            //获取Sale记录的id(在插入时由数据库自动生成)
            ResultSet rs=stat.getGeneratedKeys();
            rs.next();
            long id=rs.getLong(1);
            sale.setId(id);
            rs.close();
            //使用batch(批量)处理插入所有lineItem
            stat2=conn.prepareStatement("insert into saleItem(sale_id,product_id,quantity)" +
                    "values(?,?,?)");
            for (SalesLineItem item:sale.getLineItems()) {
                stat2.setLong(1, sale.getId());
                stat2.setLong(2,item.getProductDescription().getId());
                stat2.setInt(3,item.getQuantity());
                stat2.addBatch();
            }
            stat2.executeBatch();
            return id;
        } catch (Exception e) {
            //如果出错,则回滚整个事务
            conn.rollback();
            throw e;
        } finally{
            if (stat!=null)
                stat.close();
            if (stat2!=null)
                stat2.close();
            conn.commit(); //最终提交事务
            conn.close();
        }
    }

    public List<Sale> listSales() throws SQLException {
        Connection conn=persistManager.getConnection();
        Statement statSale = null;
        try {
            statSale = conn.createStatement();
            ResultSet rsSale=statSale.executeQuery("select * from sale");
            PreparedStatement statItem=conn.prepareStatement("select * from saleItem where sale_id=?");
            List<Sale> sales=new ArrayList<>();
            while (rsSale.next()) {
                long id=rsSale.getLong("id");
                Date date=rsSale.getDate("saleTime");
                //获取Payment
                Payment payment=null;
                int amount=rsSale.getInt("payAmount");
                PaymentType paymentType=PaymentType.valueOf(rsSale.getString("paymentType"));
                switch(paymentType) {
                    case ByCash:
                        payment=new CashPayment(amount);
                        break;
                    case ByCreditCard:
                        payment=new CreditCardPayment(amount);
                        break;
                    case ByCheck:
                        payment=new CheckPayment(amount);
                        break;
                }
                //获取SaleLineItems
                statItem.setLong(1,id);
                ResultSet rsItem=statItem.executeQuery();
                List<SalesLineItem> salesLineItems=new ArrayList<>();
                while (rsItem.next()) {
                    long productId=rsItem.getLong("product_id");
                    int quantity=rsItem.getInt("quantity");
                    SalesLineItem item=new SalesLineItem(productDescriptionPersistor.retrieve(productId),quantity);
                    salesLineItems.add(item);
                }
                Sale sale=new Sale(id,salesLineItems,payment,date);
                sales.add(sale);
            }
            return sales;
        } finally {
            if (statSale!=null){
                statSale.close();
            }
            conn.close();
        }
    }
}

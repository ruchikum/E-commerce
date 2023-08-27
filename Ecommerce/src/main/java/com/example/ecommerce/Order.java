package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrder(Customer customer,Product product){
        String Group_Order_Id = "select max(Group_Order_Id) +1 Id from orders";
        DbConnection dbConnection = new DbConnection();
        try {
            ResultSet res = dbConnection.getQueryTable(Group_Order_Id);
            if(res.next()){
                String placeOrder = "insert into orders(Group_Order_Id,Customer_Id,Product_Id)values("+res.getInt("Id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDatabase(placeOrder) != 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){
        String Group_Order_Id = "select max(Group_Order_Id) +1 Id from orders";
        DbConnection dbConnection = new DbConnection();
        try {
            ResultSet res = dbConnection.getQueryTable(Group_Order_Id);
            int count = 0;
            if(res.next()){
                for(Product product:productList){
                    String placeOrder = "insert into orders(Group_Order_Id,Customer_Id,Product_Id)values("+res.getInt("Id")+","+customer.getId()+","+product.getId()+")";
                }
                return count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}

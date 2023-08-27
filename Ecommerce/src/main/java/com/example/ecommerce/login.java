package com.example.ecommerce;

import java.sql.ResultSet;

public class login {
    public Customer customerLogin(String userName,String password){
        String query = "select * from customer where Email = '"+userName+"' and Pass = '"+password+"'";
        DbConnection connection = new DbConnection();
        try {
            ResultSet rs = connection.getQueryTable(query);
            if(rs.next()) return new Customer(rs.getInt("Id"),rs.getString("Name"),rs.getString("Email"),rs.getString("Mobile_No"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        login Login = new login();
        Customer customer = Login.customerLogin("amanchauhanru2000@gmail.com","Amansruchi");
        System.out.println("Welcome "+customer.getName());
      //  System.out.println(Login.customerLogin("amanchauhanru2000@gmail.com","Aman@123"));
    }
}

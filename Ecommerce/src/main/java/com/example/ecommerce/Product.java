package com.example.ecommerce;

import javafx.beans.property.ReadOnlySetProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public Product(int id, String name,Double price) {
        this.id = new SimpleIntegerProperty(id);
       this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }
    public int getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public double getPrice() {
        return price.get();
    }
    public static ObservableList<Product> getAllProducts(){
        String selectAllProducts = "SELECT Id,Name,Price  FROM product;";
        return fetchProductData(selectAllProducts);
    }
    public static ObservableList<Product> fetchProductData(String query) {
        ObservableList<Product> data = FXCollections.observableArrayList();
        DbConnection dbConnection = new DbConnection();
        try {
            ResultSet res = dbConnection.getQueryTable(query);
            while (res.next()) {
                Product product = new Product(res.getInt("Id"), res.getString("Name"), res.getDouble("Price"));
                data.add(product);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

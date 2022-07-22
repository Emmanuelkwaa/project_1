package com.skillstorm.models;

import java.util.Date;

public class Order {
    private int id;
    private int quantity; //amount of product ordered
    private double totalCost;
    private Date orderDate = new Date();
    private Product product;
    private Customer customer;

    public Order() {
    }

    public Order(Product product, int quantity, Customer customer, double totalCost, Date orderDate) {
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
        this.totalCost = totalCost;
        this.orderDate = orderDate;
    }

    public Order(int id, int quantity, double totalCost, Date orderDate, Product product, Customer customer) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
        this.totalCost = totalCost;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}

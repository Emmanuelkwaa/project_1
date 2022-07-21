package com.skillstorm.models;

public class Product {
    private int id;
    private String name;
    private double price;
    private String details;
    private int availableQuantity;
    private Category category;

    public Product() {
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(String name, double price, String details, int availableQuantity, Category category) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.availableQuantity = availableQuantity;
        this.category = category;
    }

    public Product(int id, String name, double price, String details, int availableQuantity, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.details = details;
        this.availableQuantity = availableQuantity;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", details='" + details + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", category=" + category +
                '}';
    }
}

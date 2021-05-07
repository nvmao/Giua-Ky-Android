package com.group.foodmanagement.model;

public class Product {
    private int id;
    private int imageId;
    private String name;
    private float price;
    private int inStock;

    public  Product(){}

    public Product(int id, int imageId, String name, float price,int inStock) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
    }
    public Product(int imageId, String name, float price,int inStock) {
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

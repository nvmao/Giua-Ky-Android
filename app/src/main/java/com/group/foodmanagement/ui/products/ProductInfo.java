package com.group.foodmanagement.ui.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductInfo extends ViewModel {
    public String Id;
    public String ProductName;
    public Double Quantity ;
    public Double Price;
    public int Image;
    public ProductInfo(String id,String productName,Double quantity,Double price,int image) {
        this.Id = id;
        this.ProductName = productName;
        this.Quantity = quantity;
        this.Price=price;
        this.Image = image;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }
}

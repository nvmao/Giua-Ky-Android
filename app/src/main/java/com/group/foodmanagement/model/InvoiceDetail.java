package com.group.foodmanagement.model;

public class InvoiceDetail {
    private int id;
    private int productId;
    private int count;
    private int invoiceId;
    private Product product;

    public InvoiceDetail(){}

    public InvoiceDetail(int productId, int count, Product product) {
        this.productId = productId;
        this.count = count;
        this.product = product;
    }

    public InvoiceDetail(int id, int productId, int count, Product product,int invoiceId) {
        this.id = id;
        this.productId = productId;
        this.count = count;
        this.product = product;
        this.invoiceId = invoiceId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
}

package com.group.foodmanagement.model;

import java.util.Date;
import java.util.List;

public class Invoice {

    private int id;
    private String created_at;
    private String created_by;
    private String status = "OK";
    private List<InvoiceDetail> invoiceDetails;


    public Invoice(){}

    public Invoice(String created_at, String created_by) {
        this.created_at = created_at;
        this.created_by = created_by;
    }

    public Invoice(int id, String created_at, String created_by, List<InvoiceDetail> invoiceDetails) {
        this.id = id;
        this.created_at = created_at;
        this.created_by = created_by;
        this.invoiceDetails = invoiceDetails;
    }

    public Invoice(int id, String created_at, String created_by) {
        this.id = id;
        this.created_at = created_at;
        this.created_by = created_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String stats){
        this.status = stats;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", created_at='" + created_at + '\'' +
                ", created_by='" + created_by + '\'' +
                ", status='" + status + '\'' +
                ", invoiceDetails=" + invoiceDetails +
                '}';
    }
}

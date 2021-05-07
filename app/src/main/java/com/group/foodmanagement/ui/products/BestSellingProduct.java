package com.group.foodmanagement.ui.products;

import android.content.Context;

import com.group.foodmanagement.model.BestSelling;
import com.group.foodmanagement.model.Invoice;
import com.group.foodmanagement.model.InvoiceDetail;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.InvoiceDetailRepository;
import com.group.foodmanagement.repository.InvoiceRepository;
import com.group.foodmanagement.repository.ProductRepository;
import com.group.foodmanagement.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class BestSellingProduct {

    List<BestSelling> products;
    List<Invoice> invoices;

    public BestSellingProduct(Context context){

        products = new ArrayList<>();

        ProductRepository productRepository = new ProductRepository(context);
        InvoiceRepository invoiceRepository = new InvoiceRepository(context);
        InvoiceDetailRepository invoiceDetailRepository = new InvoiceDetailRepository(context);

        invoices = invoiceRepository.getInvoicesByUser(UserRepository.getLoginUser().getUsername());

        for(int i = 0 ; i < invoices.size();i++){
            List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.getInvoicesDetail(invoices.get(i).getId());
            for(int j = 0 ; j < invoiceDetails.size();j++){
                Product product = productRepository.getProduct(invoiceDetails.get(j).getProductId());
                invoiceDetails.get(j).setProduct(product);
            }
            invoices.get(i).setInvoiceDetails(invoiceDetails);
        }
    }

    public List<BestSelling> topFive(){
        for (Invoice invoice : invoices){
            for (InvoiceDetail invoiceDetail : invoice.getInvoiceDetails()){
                int existProductId = existProduct(invoiceDetail.getProduct().getId());
                if(existProductId != -1){
                    int prevCount = products.get(existProductId).getCount();
                    products.get(existProductId).setCount(prevCount + invoiceDetail.getCount());
                }
                else{
                    products.add(new BestSelling(invoiceDetail.getProduct(),invoiceDetail.getCount()));
                }
            }
        }

        return products;
    }

    int existProduct(int id){
        for (int i = 0 ; i < products.size();i++){
            if(products.get(i).getProduct().getId() == id ){
                return i;
            }
        }
        return -1;
    }

}

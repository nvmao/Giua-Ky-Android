package com.group.foodmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.group.foodmanagement.R;
import com.group.foodmanagement.model.Invoice;
import com.group.foodmanagement.model.InvoiceDetail;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.InvoiceDetailRepository;
import com.group.foodmanagement.repository.ProductRepository;
import com.group.foodmanagement.ui.invoices.AddInvoiceActivity;

import java.util.List;

public class InvoiceGridAdapter extends BaseAdapter {
    Context c;
    List<Invoice> invoiceList;


    public InvoiceGridAdapter(Context c,List<Invoice> invoices){
        this.c = c;
        this.invoiceList = invoices;
    }

    @Override
    public int getCount() {
        return invoiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_grid_invoice,null);

        TextView invoiceIdText = convertView.findViewById(R.id.invoiceIdText);
        TextView invoiceIdDate = convertView.findViewById(R.id.invoiceDateText);
        TextView invoiceTotalItem = convertView.findViewById(R.id.invoiceTotalItem);
        TextView invoiceTotalPrice = convertView.findViewById(R.id.invoiceTotalPrice);

        InvoiceDetailRepository invoiceDetailRepository = new InvoiceDetailRepository(c);
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.getInvoicesDetail(invoiceList.get(position).getId());

        System.out.println(invoiceDetails.size() + " : " + invoiceList.get(position).getId());

        if(invoiceDetails != null && invoiceDetails.size() > 0){
            invoiceTotalItem.setText(String.valueOf(invoiceDetails.size()) + " items");
            invoiceTotalPrice.setText(String.valueOf(this.totalPrice(invoiceDetails)) + "$");
        }
        else{
            invoiceTotalItem.setText("0 item");
            invoiceTotalPrice.setText("0$");
        }

//        System.out.println(invoiceList.get(position));

        invoiceIdText.setText(String.valueOf(invoiceList.get(position).getId()));
        invoiceIdDate.setText(invoiceList.get(position).getCreated_at());

        Button viewInvoiceBtn = convertView.findViewById(R.id.viewInvoiceBtn);
        viewInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(c, AddInvoiceActivity.class);
                myIntent.putExtra("invoice_id", invoiceList.get(position).getId()); //Optional parameters
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(myIntent);
            }
        });

        return convertView;
    }

    float totalPrice(List<InvoiceDetail> invoiceDetails){
        float price = 0;
        for(InvoiceDetail detail : invoiceDetails){
            ProductRepository productRepository = new ProductRepository(c);
            Product product = productRepository.getProduct(detail.getProductId());

            float p = 0;

            if(product != null){
                p = detail.getCount() * product.getPrice();

            }
            price += p;

        }
        return price;
    }

}

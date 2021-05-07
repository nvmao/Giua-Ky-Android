package com.group.foodmanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.group.foodmanagement.R;
import com.group.foodmanagement.model.Invoice;
import com.group.foodmanagement.model.InvoiceDetail;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.ProductRepository;

import java.util.List;

public class InvoiceDetailGridAdapter extends BaseAdapter {
    Context c;
    List<InvoiceDetail> invoiceDetails;
    GridView gridView;

    public GridView getGridView() {
        return gridView;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    public InvoiceDetailGridAdapter(Context c, List<InvoiceDetail> invoiceDetails){
        this.c = c;
        this.invoiceDetails = invoiceDetails;
    }

    @Override
    public int getCount() {
        return invoiceDetails.size();
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
        convertView = layoutInflater.inflate(R.layout.adapter_grid_invoice_detail,null);

        TextView invoiceDetailId = convertView.findViewById(R.id.invoiceDetailIdText);
        TextView invoiceDetailPrice = convertView.findViewById(R.id.invoiceDetailPrice);
        TextView invoiceDetailCount = convertView.findViewById(R.id.invoiceDetailCount);
        TextView invoiceDetailTotalPrice = convertView.findViewById(R.id.invoiceDetailTotalPrice);
        ImageView invoiceDetailImage = convertView.findViewById(R.id.invoiceDetailImage);
        Button removeInvoiceDetailBtn = convertView.findViewById(R.id.removeInvoiceDetailBtn);

        invoiceDetailId.setText(String.valueOf(invoiceDetails.get(position).getProductId()));
        invoiceDetailImage.setImageResource(invoiceDetails.get(position).getProduct().getImageId());
        invoiceDetailPrice.setText(String.valueOf(invoiceDetails.get(position).getProduct().getPrice()) + "$");
        invoiceDetailCount.setText(String.valueOf(invoiceDetails.get(position).getCount()));
        invoiceDetailTotalPrice.setText(String.valueOf(invoiceDetails.get(position).getCount() * invoiceDetails.get(position).getProduct().getPrice()) + "$");

        final InvoiceDetailGridAdapter adapter = this;

        if(gridView != null){
            removeInvoiceDetailBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    invoiceDetails.remove(position);
                    gridView.setAdapter(adapter);
                }
            });
        }


        return convertView;
    }

}
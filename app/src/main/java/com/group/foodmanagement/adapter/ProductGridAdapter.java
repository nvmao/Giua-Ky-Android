package com.group.foodmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.group.foodmanagement.R;
import com.group.foodmanagement.model.Product;

import java.util.List;

public class ProductGridAdapter extends BaseAdapter {
    Context c;
    List<Product> productList;


    public ProductGridAdapter(Context c,List<Product> products){
        this.c = c;
        this.productList = products;
    }

    @Override
    public int getCount() {
        return productList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adaper_grid_product,null);

        ImageView productImage = convertView.findViewById(R.id.product_grid_image);
        TextView productName = convertView.findViewById(R.id.product_grid_name);
        TextView productInStock = convertView.findViewById(R.id.product_grid_inStock);
        TextView productPrice = convertView.findViewById(R.id.product_grid_price);



        productName.setText("Name: " + productList.get(position).getName());
        productInStock.setText("InStock: " + String.valueOf(productList.get(position).getInStock()));
        productPrice.setText("Price: " + String.valueOf(productList.get(position).getPrice()) + "$");

        productImage.setImageResource(productList.get(position).getImageId());

        return convertView;
    }

}

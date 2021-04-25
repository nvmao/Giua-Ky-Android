package com.group.foodmanagement.ui.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.group.foodmanagement.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ProductInfo> ListproductInfo;

    public ProductAdapter(Context context, int layout, List<ProductInfo> listProductInfo) {
        this.context = context;
        this.layout = layout;
        this.ListproductInfo = listProductInfo;
    }

    @Override
    public int getCount() {
        return ListproductInfo.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView name = (TextView) convertView.findViewById(R.id.TvItemName);
        TextView quan = (TextView) convertView.findViewById(R.id.TvItemQuan);
        TextView price = (TextView) convertView.findViewById(R.id.ItemPrice);
        ImageView image = (ImageView) convertView.findViewById((R.id.imageView2));

        ProductInfo productInfo = ListproductInfo.get(position);
        name.setText(productInfo.ProductName);
        String quantity = Double.toString(productInfo.Quantity);
        quan.setText(quantity);
        String price1 =  Double.toString(productInfo.Price);
        price.setText(price1);
        image.setImageResource(productInfo.getImage());

        return convertView;
    }
}

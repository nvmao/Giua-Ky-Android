package com.group.foodmanagement.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.group.foodmanagement.MainActivity;
import com.group.foodmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    public ArrayList<ProductInfo> listProductInfos = new ArrayList<>();
    public EditText edtProductName ;
    public  EditText edtQuantity ;
    public  EditText edtPrice ;
    public EditText searchfill;
    public ImageView Image;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        listProductInfos.add(new ProductInfo("1","banana",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("2","water melon",12.0,33000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("3","orange",100.0,44000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("4","mango",120.0,520000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("5","Apple",92.0,120000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("6","Berry",33.0,550000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("7","Cherry",52.0,640000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("8","Dates",72.0,50000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("9","Guava",112.0,770000.0,R.drawable.traicay));

        ListView lvData = (ListView) root.findViewById(R.id.LvProduct);

        final ProductAdapter adapter = new ProductAdapter(getContext(),R.layout.itemproduct,listProductInfos);
        lvData.setAdapter(adapter);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtProductName = (EditText) view.findViewById(R.id.EdtProductName);
                edtProductName.setText(listProductInfos.get((int) id).ProductName);
            }
        });
        return root;
    }
    public View add(@NonNull LayoutInflater inflater,
                    ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        edtProductName = (EditText) root.findViewById(R.id.EdtProductName);
        edtQuantity = (EditText) root.findViewById(R.id.EdtQuantity);
        edtPrice = (EditText) root.findViewById(R.id.EdtPrice);
        Image = (ImageView) root.findViewById(R.id.imageView);
        final ProductAdapter adapter = new ProductAdapter(getContext(),R.layout.itemproduct,listProductInfos);
        ProductInfo newInfo = new ProductInfo(
                "1", edtProductName.getText().toString(),Double.parseDouble(edtQuantity.getText().toString()) ,Double.parseDouble(edtPrice.getText().toString()),Image.getImageAlpha()
        );
        listProductInfos.add(newInfo);
        adapter.notifyDataSetChanged();
        return root;
    }
    public View search(@NonNull LayoutInflater inflater,
                       ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        searchfill = (EditText) root.findViewById(R.id.SearchEdt);
        ArrayList<ProductInfo> List = new ArrayList<>();
        for(ProductInfo item : listProductInfos){
            if(item.ProductName.contains(searchfill.getText().toString())){
                List.add(item);
            }
        }
        final ProductAdapter adapter = new ProductAdapter(getContext(),R.layout.itemproduct,List);
        ListView lvData = (ListView) root.findViewById(R.id.LvProduct);
        lvData.setAdapter(adapter);
        return root;
    }
    public View delete(@NonNull LayoutInflater inflater,
                    ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        edtProductName = (EditText) root.findViewById(R.id.EdtProductName);
        edtQuantity = (EditText) root.findViewById(R.id.EdtQuantity);
        edtPrice = (EditText) root.findViewById(R.id.EdtPrice);
        Image = (ImageView) root.findViewById(R.id.imageView);
        final ProductAdapter adapter = new ProductAdapter(getContext(),R.layout.itemproduct,listProductInfos);
        if(edtProductName.getText().toString() == "" || edtProductName.getText().toString() == null){

        }
        else {
            for(ProductInfo item : listProductInfos){
                if(item.ProductName == edtProductName.getText().toString()){
                    listProductInfos.remove(item);
                }
            }
            ListView lvData = (ListView) root.findViewById(R.id.LvProduct);
            lvData.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();
        return root;
    }
    public View edit(@NonNull LayoutInflater inflater,
                       ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        edtProductName = (EditText) root.findViewById(R.id.EdtProductName);
        edtQuantity = (EditText) root.findViewById(R.id.EdtQuantity);
        edtPrice = (EditText) root.findViewById(R.id.EdtPrice);
        Image = (ImageView) root.findViewById(R.id.imageView);
        ProductInfo edit = new ProductInfo("1",edtProductName.getText().toString(),Double.parseDouble(edtQuantity.getText().toString()),
                Double.parseDouble(edtPrice.getText().toString()),1);
        final ProductAdapter adapter = new ProductAdapter(getContext(),R.layout.itemproduct,listProductInfos);
        if(edtProductName.getText().toString() == "" || edtProductName.getText().toString() == null||
                edtQuantity.getText().toString() == "" || edtQuantity.getText().toString() == null||
                edtPrice.getText().toString() == "" || edtPrice.getText().toString() == null||
                Image.getDrawable().toString() == "" || Image.getDrawable().toString() == null){

        }
        else {

            for(ProductInfo item : listProductInfos){
                if(item.ProductName == edtProductName.getText().toString()){
                    listProductInfos.remove(item);
                    // chua hoan thanh ham sua
                }
            }
            ListView lvData = (ListView) root.findViewById(R.id.LvProduct);
            lvData.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();
        return root;
    }
}

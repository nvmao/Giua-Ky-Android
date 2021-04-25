package com.group.foodmanagement.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        listProductInfos.add(new ProductInfo("1","banana",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("2","water melon",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("3","orange",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("4","mango",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("5","Apple",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("6","Berry",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("7","Cherry",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("8","Dates",12.0,100000.0,R.drawable.traicay));
        listProductInfos.add(new ProductInfo("9","Guava",12.0,100000.0,R.drawable.traicay));
//        ArrayAdapter <ProductInfo> adapter = new ArrayAdapter<ProductInfo>(
//            getContext(),
//            android.R.layout.simple_list_item_single_choice,listProductInfos);
        ListView lvData = (ListView) root.findViewById(R.id.LvProduct);
//        lvData.setAdapter(adapter);
        final ProductAdapter adapter = new ProductAdapter(getContext(),R.layout.itemproduct,listProductInfos);
        lvData.setAdapter(adapter);
        Button add = (Button) root.findViewById(R.id.BtnAdd);
        Button del = (Button) root.findViewById(R.id.BtnDel);
        Button edit = (Button) root.findViewById(R.id.BtnEdit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView edtProductName = (TextView) v.findViewById(R.id.EdtProductName);
                TextView edtQuantity = (TextView) v.findViewById(R.id.EdtQuantity);
                TextView edtPrice = (TextView) v.findViewById(R.id.EdtPrice);
                ImageView imageView = (ImageView) v.findViewById((R.id.imageView));
                ProductInfo newInfo = new ProductInfo(
                    "1", edtProductName.getText().toString(),Double.parseDouble(edtQuantity.getText().toString()) ,Double.parseDouble(edtPrice.getText().toString()),1
                );
                listProductInfos.add(newInfo);
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }


}

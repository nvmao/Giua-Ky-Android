package com.group.foodmanagement.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.group.foodmanagement.R;
import com.group.foodmanagement.adapter.ProductGridAdapter;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.ProductRepository;

import java.util.List;

public class ProductsFragment extends Fragment {
    GridView gridView ;
    private ProductsViewModel productsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel =
                ViewModelProviders.of(this).get(ProductsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        ProductRepository db = new ProductRepository(getActivity());
        List<Product> products = db.getAllProducts();

        gridView = (GridView) root.findViewById(R.id.gridView);
        ProductGridAdapter ad = new ProductGridAdapter(getActivity().getApplicationContext(),products);
        gridView.setAdapter(ad);

        return root;
    }

    @Override
    public void onResume() {
        ProductRepository db = new ProductRepository(getActivity());
        List<Product> products = db.getAllProducts();
        ProductGridAdapter ad = new ProductGridAdapter(getActivity().getApplicationContext(),products);
        gridView.setAdapter(ad);
        super.onResume();
    }
}

package com.group.foodmanagement.ui.products;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group.foodmanagement.R;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.ProductRepository;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;



public class AddProductActivity extends AppCompatActivity {

    Product product;
    Map<String,Integer> imageMap;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        this.initImageMap();

        Intent intent = getIntent();
        int invoice_id = intent.getIntExtra("product_id",0);
        if(invoice_id != 0){
            ProductRepository invoiceRepository = new ProductRepository(this);
            product = invoiceRepository.getProduct(invoice_id);

            System.out.println("update: " + product.getName());
            initUpdate();
        }
        else{
            initCreate();
            System.out.println("create");
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    void initUpdate(){
        Button addBtn = findViewById(R.id.addBtn);

        final Spinner spinner = (Spinner) findViewById(R.id.imageIdSpiner);
        final ImageView productImage = findViewById(R.id.imageProductView);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                productImage.setImageResource(imageMap.get(item));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("breach");
        categories.add("nui");
        categories.add("food1");
        categories.add("food2");
        categories.add("pasta");
        categories.add("perry");
        categories.add("pizza");
        categories.add("taco");
        categories.add("tor");
        categories.add("chanh");
        categories.add("mcdonal");



        TextView nameText = (TextView) findViewById(R.id.nameText);
        TextView priceText = (TextView) findViewById(R.id.priceText);
        TextView inStockText = (TextView) findViewById(R.id.inStockText);

        nameText.setText(product.getName());
        priceText.setText(String.valueOf(product.getPrice()));
        inStockText.setText(String.valueOf(product.getInStock()));


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        imageMap.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String s, Integer integer) {
                if(integer == product.getImageId()){
                    productImage.setImageResource(imageMap.get(s));
                    for(int i = 0 ; i < categories.size();i++){
                        if(categories.get(i).equals(s)){
                            spinner.setSelection(i);
                        }
                    }
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductRepository db = new ProductRepository(getApplicationContext());

                TextView nameText =  findViewById(R.id.nameText);
                TextView priceText =  findViewById(R.id.priceText);
                TextView inStockText =  findViewById(R.id.inStockText);

                String name = nameText.getText().toString();
                float price = Float.parseFloat (priceText.getText().toString());
                int imageId = imageMap.get(spinner.getSelectedItem());

                int inStock = Integer.parseInt(inStockText.getText().toString());

                product.setName(name);
                product.setInStock(inStock);
                product.setPrice(price);
                product.setImageId(imageId);

                db.updateProduct(product);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    void initCreate(){
        Button addBtn = findViewById(R.id.addBtn);

        final Spinner spinner = (Spinner) findViewById(R.id.imageIdSpiner);
        final ImageView productImage = findViewById(R.id.imageProductView);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                productImage.setImageResource(imageMap.get(item));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("breach");
        categories.add("nui");
        categories.add("food1");
        categories.add("food2");
        categories.add("pasta");
        categories.add("perry");
        categories.add("pizza");
        categories.add("taco");
        categories.add("tor");
        categories.add("chanh");
        categories.add("mcdonal");

        productImage.setImageResource(imageMap.get("breach"));



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductRepository db = new ProductRepository(getApplicationContext());

                TextView nameText = (TextView) findViewById(R.id.nameText);
                TextView priceText = (TextView) findViewById(R.id.priceText);
                TextView inStockText = (TextView) findViewById(R.id.inStockText);

                String name = nameText.getText().toString();
                float price = Float.parseFloat (priceText.getText().toString());
                int imageId = imageMap.get(spinner.getSelectedItem());
                int inStock = Integer.parseInt(inStockText.getText().toString());

                Product product = new Product(imageId,name,price,inStock);

                db.addProduct(product);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    void initImageMap(){
        this.imageMap = new Hashtable<>();
        imageMap.put("breach",R.drawable.banh);
        imageMap.put("nui",R.drawable.nui);
        imageMap.put("food1",R.drawable.food);
        imageMap.put("food2",R.drawable.food2);
        imageMap.put("pasta",R.drawable.pasta);
        imageMap.put("perry",R.drawable.perry);
        imageMap.put("pizza",R.drawable.pizza);
        imageMap.put("taco",R.drawable.taco);
        imageMap.put("tor",R.drawable.tor);
        imageMap.put("chanh",R.drawable.chanh);
        imageMap.put("mcdonal",R.drawable.mcdonal);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
    }

}
package com.group.foodmanagement.ui.products;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.group.foodmanagement.MainActivity;
import com.group.foodmanagement.R;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.InvoiceRepository;
import com.group.foodmanagement.repository.ProductRepository;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class AddProductActivity extends AppCompatActivity {

    Product product;
    Map<String,Integer> imageMap;
        ImageView  productImage;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


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
        Button chooseImageBtn =findViewById(R.id.chooseImgBtn);
        Button addBtn = findViewById(R.id.addBtn);

        final Spinner spinner = (Spinner) findViewById(R.id.imageIdSpiner);
        final ImageView productImage = findViewById(R.id.imageProductView);


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






        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductRepository db = new ProductRepository(getApplicationContext());

                TextView nameText =  findViewById(R.id.nameText);
                TextView priceText =  findViewById(R.id.priceText);
                TextView inStockText =  findViewById(R.id.inStockText);

                String name = nameText.getText().toString();
                float price = Float.parseFloat (priceText.getText().toString());
                int imageId = productImage.getId();
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
Button chooseImageBtn =findViewById(R.id.chooseImgBtn);
        final Spinner spinner = (Spinner) findViewById(R.id.imageIdSpiner);

        productImage = findViewById(R.id.imageProductView);

        // Spinner click listener
       chooseImageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
            }
                });
        // Spinner click listener





        // Creating adapter for spinner

        // Drop down layout style - list view with radio button

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



    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
    }
private void pickImageFromGallery() {
Intent intent=new Intent(Intent.ACTION_PICK);
intent.setType("image/*");
startActivityForResult(intent,IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
            switch (requestCode) {
                case PERMISSION_CODE: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickImageFromGallery();
                    } else {
                        Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            productImage.setImageURI(data.getData());

        }
    }
}

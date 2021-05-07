package com.group.foodmanagement.ui.products;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.group.foodmanagement.R;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.ProductRepository;

public class AddProductActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Button addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductRepository db = new ProductRepository(getApplicationContext());

                TextView nameText = (TextView) findViewById(R.id.nameText);
                TextView priceText = (TextView) findViewById(R.id.priceText);
                TextView inStockText = (TextView) findViewById(R.id.inStockText);

                String name = nameText.getText().toString();
                float price = Float.parseFloat (priceText.getText().toString());
                int imageId = R.drawable.food2;
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
}
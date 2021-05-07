package com.group.foodmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.group.foodmanagement.adapter.ProductGridAdapter;
import com.group.foodmanagement.repository.InvoiceDetailRepository;
import com.group.foodmanagement.repository.InvoiceRepository;
import com.group.foodmanagement.repository.ProductRepository;
import com.group.foodmanagement.ui.invoices.AddInvoiceActivity;
import com.group.foodmanagement.ui.login.LoginActivity;
import com.group.foodmanagement.ui.products.AddProductActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_invoices, R.id.navigation_products, R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
////        myIntent.putExtra("key", value); //Optional parameters
//        MainActivity.this.startActivity(myIntent);

        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        InvoiceRepository invoiceRepository = new InvoiceRepository(getApplicationContext());
        InvoiceDetailRepository invoiceDetailRepository = new InvoiceDetailRepository(getApplicationContext());

//        productRepository.reset();
//        invoiceRepository.reset();
//        invoiceDetailRepository.reset();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.right_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refeshBtn:{
                System.out.println("refresh");
                return true;
            }
            case R.id.createInvocieBtn:{
                Intent myIntent = new Intent(MainActivity.this, AddInvoiceActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            }
            case R.id.addProductBtn:{
                Intent myIntent = new Intent(MainActivity.this, AddProductActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            }
        }
        return false;
    }
}

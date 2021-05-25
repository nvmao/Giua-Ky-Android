package com.group.foodmanagement.ui.invoices;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.group.foodmanagement.R;
import com.group.foodmanagement.adapter.InvoiceDetailGridAdapter;
import com.group.foodmanagement.adapter.InvoiceGridAdapter;
import com.group.foodmanagement.model.Invoice;
import com.group.foodmanagement.model.InvoiceDetail;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.repository.InvoiceDetailRepository;
import com.group.foodmanagement.repository.InvoiceRepository;
import com.group.foodmanagement.repository.ProductRepository;
import com.group.foodmanagement.repository.UserRepository;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddInvoiceActivity extends AppCompatActivity {

    List<InvoiceDetail> invoiceDetails = new ArrayList<>();
    Invoice invoice = new Invoice();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);

        Intent intent = getIntent();
        int invoice_id = intent.getIntExtra("invoice_id",0);
        if(invoice_id != 0){
            InvoiceRepository invoiceRepository = new InvoiceRepository(this);
            invoice = invoiceRepository.getInvoice(invoice_id);
            initUpdate();
        }
        else{
            initCreate();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void initUpdate() {
        TextView invoiceDate = (TextView) findViewById(R.id.invoiceDate);
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        invoiceDate.setEnabled(false);
        invoiceDate.setText(invoice.getCreated_at() +" : "+ invoice.getCreated_by());


        Button addInvoiceDetailBtn = findViewById(R.id.addInvoiceDetailBtn);
        Button createInvoiceBtn = findViewById(R.id.createDoneInvoiceBtn);

        addInvoiceDetailBtn.setVisibility(View.INVISIBLE);
        createInvoiceBtn.setText("Canceled this invoice");
        createInvoiceBtn.setBackgroundColor(Color.argb(100,150,20,20));

        createInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {

                invoice.setStatus("CANCELED");

                InvoiceRepository invoiceRepository = new InvoiceRepository(getApplicationContext());
                invoiceRepository.updateInvoice(invoice);

                ProductRepository productRepository = new ProductRepository(getApplicationContext());

                for(InvoiceDetail detail : invoiceDetails){
                    Product product = productRepository.getProduct(detail.getProductId());
                    product.setInStock(product.getInStock() + detail.getCount());

                    productRepository.updateProduct(product);
                }

                setResult(Activity.RESULT_OK);
                finish();
            }
        });


        Spinner productIdText = findViewById(R.id.productIdText);
        TextView productCountText = findViewById(R.id.productCountText);
        productIdText.setVisibility(View.INVISIBLE);
        productCountText.setVisibility(View.INVISIBLE);


        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        InvoiceDetailRepository invoiceDetailRepository = new InvoiceDetailRepository(getApplicationContext());

        List<InvoiceDetail> detailsDb = invoiceDetailRepository.getInvoicesDetail(invoice.getId());
        final GridView gridView = (GridView) findViewById(R.id.invoiceDetailGridView);

        if(detailsDb.size() > 0){
            for(InvoiceDetail detail: detailsDb){
                Product product = productRepository.getProduct(detail.getProductId());
                int productBuyCount = detail.getCount();

                InvoiceDetail detailItem = new InvoiceDetail(product.getId(),productBuyCount,product);
                invoiceDetails.add(detailItem);
            }
        }

        InvoiceDetailGridAdapter ad = new InvoiceDetailGridAdapter(getApplicationContext(),invoiceDetails);
        gridView.setAdapter(ad);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void initCreate(){
        TextView invoiceDate = (TextView) findViewById(R.id.invoiceDate);
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        invoiceDate.setEnabled(false);
        invoiceDate.setText((dtf.format(now).toString()));

        final GridView gridView = (GridView) findViewById(R.id.invoiceDetailGridView);
        InvoiceDetailGridAdapter ad = new InvoiceDetailGridAdapter(getApplicationContext(),this.invoiceDetails);
        ad.setGridView(gridView);
        gridView.setAdapter(ad);

        Button addInvoiceDetailBtn = findViewById(R.id.addInvoiceDetailBtn);
        Button createInvoiceBtn = findViewById(R.id.createDoneInvoiceBtn);

        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        final Spinner spinner = (Spinner) findViewById(R.id.productIdText);

        final List<String> list= productRepository.getAllProduct();
        List<String> listNameProduct = new ArrayList<String>();
        for(int i = 0 ; i < list.size() ; i++)
        {
            String[] filter = list.get(i).split("_");
            listNameProduct.add(filter[1]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listNameProduct);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        addInvoiceDetailBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductRepository productRepository = new ProductRepository(getApplicationContext());
                TextView productCountText = findViewById(R.id.productCountText);

                int pos = spinner.getSelectedItemPosition();
                String[] fit = list.get(pos).split("_");
                Product product = productRepository.getProduct(Integer.parseInt(fit[0]));

                int productBuyCount = Integer.parseInt(productCountText.getText().toString());

                if(product != null && productBuyCount <= product.getInStock()){

                    int existDetail = isExistDetail(product.getId());
                    if(existDetail != -1){
                        if(productBuyCount + invoiceDetails.get(existDetail).getCount() < invoiceDetails.get(existDetail).getProduct().getInStock()){
                            productBuyCount = productBuyCount + invoiceDetails.get(existDetail).getCount();
                            invoiceDetails.get(existDetail).setCount(productBuyCount);
                        }
                    }
                    else {
                        InvoiceDetail detail = new InvoiceDetail(product.getId(),productBuyCount,product);
                        invoiceDetails.add(detail);
                    }

                    InvoiceDetailGridAdapter ad = new InvoiceDetailGridAdapter(getApplicationContext(),invoiceDetails);
                    ad.setGridView(gridView);
                    gridView.setAdapter(ad);
                }
            }
        });

        createInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {

                if(invoiceDetails.size() == 0){
                    return;
                }

                invoice.setCreated_by(UserRepository.getLoginUser().getUsername());
                invoice.setCreated_at(dtf.format(now));

                InvoiceRepository invoiceRepository = new InvoiceRepository(getApplicationContext());
                invoiceRepository.addInvoice(invoice);

                Invoice addedInvoice = invoiceRepository.getLastInvoice();
                if(addedInvoice != null){
                    InvoiceDetailRepository invoiceDetailRepository = new InvoiceDetailRepository(getApplicationContext());
                    ProductRepository productRepository = new ProductRepository(getApplicationContext());

                    for(InvoiceDetail detail : invoiceDetails){
                        detail.setInvoiceId(addedInvoice.getId());
                        Product product = productRepository.getProduct(detail.getProductId());
                        product.setInStock(product.getInStock() - detail.getCount());

                        invoiceDetailRepository.addInvoiceDetail(detail);
                        productRepository.updateProduct(product);
                    }

                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });

    }

    int isExistDetail(int productId){
        for(int i =0 ;i < invoiceDetails.size();i++){
            if(invoiceDetails.get(i).getProduct().getId() == productId){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
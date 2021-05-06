package com.example.gk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateBill extends AppCompatActivity {

    Spinner listIdEmp,listProduct;
    Button add,addBill;
    EditText num;
    TableLayout tableLayout;
    TextView idBill,date ,money;

    private static int count = 4;
    int hold= 0,cash= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        
        setControl();
        execute();

    }



    private void execute() {
        ArrayList<String> idEmp = new ArrayList<>();
        idEmp.add("NV01");
        idEmp.add("NV02");
        idEmp.add("NV03");
        idEmp.add("NV04");

        ArrayList<Product> list = new ArrayList<>();
        Product product = new Product();
        product.setId("SP01");
        product.setName("BIA 333");
        product.setPrice(12000);
        list.add(product);

        product = new Product();
        product.setId("SP02");
        product.setName("BIA SAIGON");
        product.setPrice(15000);
        list.add(product);

        product = new Product();
        product.setId("SP03");
        product.setName("BIA TIGER");
        product.setPrice(14000);
        list.add(product);

        ArrayList<String> list1 = new ArrayList<>();
        for(int i = 0 ; i < list.size() ; i++)
        {
            list1.add(list.get(i).getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,idEmp);
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list1);

        listIdEmp.setAdapter(arrayAdapter);
        listProduct.setAdapter(arrayAdapter1);
        Date date= new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date1= format.format(date);
        this.date.setText(date1);
        idBill.setText("HD"+count);
        count++;

        tableLayout.setColumnCollapsed(4,true);
        add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            hold = listProduct.getSelectedItemPosition();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if(num.getText().toString().equals(""))
                    {

                    }
                    else
                    {
                        TextView tv1 = new TextView(v.getContext());
                        tv1.setText(list1.get(hold).toString());
                        tv1.setGravity(Gravity.CENTER);

                        TextView tv2 = new TextView(v.getContext());
                        tv2.setText(num.getText().toString());
                        tv2.setPadding(150,0,0,0);
                        tv2.setGravity(Gravity.CENTER);

                        TextView tv3 = new TextView(v.getContext());
                        String s =String.valueOf(list.get(hold).getPrice());
                        tv3.setText(s.toString());
                        tv3.setPadding(160,0,0,0);
                        tv3.setGravity(Gravity.CENTER);

                        int sl = Integer.parseInt(num.getText().toString());
                        int pri = list.get(hold).getPrice();
                        int re = sl*pri;
                        cash+= re;
                        money.setText(String.valueOf(cash));
                        TextView tv4 = new TextView(v.getContext());
                        String tt = String.valueOf(re);
                        tv4.setText(tt);
                        tv4.setPadding(170,0,0,0);
                        tv4.setGravity(Gravity.CENTER);

                        TableRow a = new TableRow(v.getContext());
                        a.setBackgroundResource(R.color.white);
                        a.addView(tv1);
                        a.addView(tv2);
                        a.addView(tv3);
                        a.addView(tv4);
                        tableLayout.addView(a);
                    }

                }
            });




    }

    private void setControl() {
        listIdEmp = (Spinner) findViewById(R.id.idEmp);
        listProduct= (Spinner) findViewById(R.id.product);
        add = (Button) findViewById(R.id.addProduct);
        addBill = (Button) findViewById(R.id.addBill);
        num = (EditText) findViewById(R.id.num);
        tableLayout = (TableLayout) findViewById(R.id.tableView);
        idBill = (TextView) findViewById(R.id.idBill);
        date = (TextView) findViewById(R.id.date);
        money = (TextView) findViewById(R.id.money);
    }
}
package com.example.gk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListBill extends AppCompatActivity {


    ArrayList<Bill> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_bill);


        try {
            addData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        execute();



    }
    private void execute(){
        final TableLayout tb =findViewById(R.id.tableView);
        tb.setColumnCollapsed(3,true);

        for(int i = 0 ; i < data.size() ; i++)
        {
            Bill b = data.get(i);
            TextView tv1 = new TextView(this);
            tv1.setText(b.getIdBill());
            tv1.setGravity(Gravity.CENTER);
            TextView tv2 = new TextView(this);
            Date date = b.getDate();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String kq = format.format(date);
            tv2.setText(kq);
            tv2.setPadding(150,0,0,0);
            tv2.setGravity(Gravity.CENTER);

            TextView tv3 = new TextView(this);
            tv3.setText(b.getIdemp());
            tv3.setPadding(200,0,0,0);
            tv3.setGravity(Gravity.CENTER);

            TableRow a = new TableRow(this);
            a.setBackgroundResource(R.color.white);
            a.addView(tv1);
            a.addView(tv2);
            a.addView(tv3);
            tb.addView(a);
        }
    }
    private void addData() throws ParseException {
        Bill bill = new Bill();
        bill.setIdBill("HD01");
        String date = "13/03/2020";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        bill.setDate(date1);
        bill.setIdemp("NV01");
        data.add(bill);


        bill = new Bill();
        bill.setIdBill("HD02");
        bill.setDate(date1);
        bill.setIdemp("NV01");
        data.add(bill);

        bill = new Bill();
        bill.setIdBill("HD03");
        String date2 = "14/03/2020";
        Date date3= new SimpleDateFormat("dd/MM/yyyy").parse(date2);
        bill.setDate(date3);
        bill.setIdemp("NV02");
        data.add(bill);

    }

}
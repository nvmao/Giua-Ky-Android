package com.group.foodmanagement.ui.user;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.group.foodmanagement.R;
import com.group.foodmanagement.model.BestSelling;
import com.group.foodmanagement.ui.products.BestSellingProduct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class UserFragment extends Fragment {

    private UserViewModel userViewModel;
    BarChart severityBarChart;
    Button statisticBtn;
    Spinner month_spinner;

    ArrayList<String> severityStringList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        severityBarChart = root.findViewById(R.id.idBarChart);
        statisticBtn = root.findViewById(R.id.statisticBtn);
        month_spinner = root.findViewById(R.id.month_spinner);
        initializeBarChart();
        initSpinner();


        statisticBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                statistic();
            }

        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void statistic()
    {
        severityStringList.clear();
        BestSellingProduct bestSellingProduct = new BestSellingProduct(getActivity());
        List<BestSelling> bestSellings = bestSellingProduct.topFiveWithMonth(month_spinner.getSelectedItem().toString());

        bestSellings.sort(new Comparator<BestSelling>() {
            @Override
            public int compare(BestSelling t1, BestSelling t2) {
                return t2.getCount() - t1.getCount();
            }
        });

        for(int i = 0 ; i < bestSellings.size();i++){

            severityStringList.add(bestSellings.get(i).getProduct().getName());
        }

        System.out.println("top: " + bestSellings.size());

        createBarChart(bestSellings);

    }

    private void initializeBarChart() {
        severityBarChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        severityBarChart.setMaxVisibleValueCount(4);
        severityBarChart.getXAxis().setDrawGridLines(false);
        // scaling can now only be done on x- and y-axis separately
        severityBarChart.setPinchZoom(false);

        severityBarChart.setDrawBarShadow(false);
        severityBarChart.setDrawGridBackground(false);

        XAxis xAxis = severityBarChart.getXAxis();
        xAxis.setDrawGridLines(false);

        severityBarChart.getAxisLeft().setDrawGridLines(false);
        severityBarChart.getAxisRight().setDrawGridLines(false);
        severityBarChart.getAxisRight().setEnabled(false);
        severityBarChart.getAxisLeft().setEnabled(true);
        severityBarChart.getXAxis().setDrawGridLines(false);
        // add a nice and smooth animation
        severityBarChart.animateY(1500);


        severityBarChart.getLegend().setEnabled(false);

        severityBarChart.getAxisRight().setDrawLabels(false);
        severityBarChart.getAxisLeft().setDrawLabels(true);
        severityBarChart.setTouchEnabled(false);
        severityBarChart.setDoubleTapToZoomEnabled(false);
        severityBarChart.getXAxis().setEnabled(true);
        severityBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        severityBarChart.invalidate();



    }
    private void initSpinner()
    {
        final List<String> categories = new ArrayList<String>();
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");
        categories.add("11");
        categories.add("12");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(dataAdapter);
    }
    private void setToInit()
    {
        System.out.println("so phan tu:" + severityStringList.size());
    }
    private void createBarChart(List<BestSelling> bestSellings) {
        initializeBarChart();
        setToInit();
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < bestSellings.size(); i++) {
            Random r = new Random();
            values.add(new BarEntry(i, bestSellings.get(i).getCount()));
        }

        if(severityStringList.size() == 0)
        {
            XAxis xAxis = severityBarChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(severityStringList));
        }
        BarDataSet set1;

        if (severityBarChart.getData() != null &&
                severityBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) severityBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            severityBarChart.getData().notifyDataChanged();
            severityBarChart.notifyDataSetChanged();
            XAxis xAxis = severityBarChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(severityStringList));
        } else {

            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            set1.setDrawValues(true);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            severityBarChart.setData(data);
            severityBarChart.setVisibleXRange(1,5);
            severityBarChart.setFitBars(true);
            XAxis xAxis = severityBarChart.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(severityStringList));//setting String values in Xaxis
            for (IDataSet set : severityBarChart.getData().getDataSets())
                set.setDrawValues(!set.isDrawValuesEnabled());

            severityBarChart.invalidate();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {

        BestSellingProduct bestSellingProduct = new BestSellingProduct(getActivity());
        List<BestSelling> bestSellings = bestSellingProduct.topFive();


        bestSellings.sort(new Comparator<BestSelling>() {
            @Override
            public int compare(BestSelling t1, BestSelling t2) {
                return t2.getCount() - t1.getCount();
            }
        });

        for(int i = 0 ; i < bestSellings.size();i++){
            severityStringList.add(bestSellings.get(i).getProduct().getName());
        }

        System.out.println("top: " + bestSellings.size());

        createBarChart(bestSellings);
        super.onResume();


    }


}

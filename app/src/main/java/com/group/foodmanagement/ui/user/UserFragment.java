package com.group.foodmanagement.ui.user;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    ArrayList<String> severityStringList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        severityBarChart = root.findViewById(R.id.idBarChart);

        initializeBarChart();


        return root;
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


    private void createBarChart(List<BestSelling> bestSellings) {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < bestSellings.size(); i++) {
            Random r = new Random();
            values.add(new BarEntry(i, bestSellings.get(i).getCount()));
        }

        BarDataSet set1;

        if (severityBarChart.getData() != null &&
                severityBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) severityBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            severityBarChart.getData().notifyDataChanged();
            severityBarChart.notifyDataSetChanged();
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

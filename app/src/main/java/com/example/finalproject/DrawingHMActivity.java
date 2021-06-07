package com.example.finalproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HeatDataEntry;
import com.anychart.charts.HeatMap;
import com.anychart.enums.ScaleTypes;
import com.anychart.enums.SelectionMode;
import com.anychart.graphics.vector.SolidFill;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class DrawingHMActivity extends AppCompatActivity {
    Matrix matrix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_h_m);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle arguments = getIntent().getExtras();

        if(arguments != null){
            matrix = (Matrix)arguments.getSerializable(Matrix.class.getSimpleName());
        }

        List<DataEntry> cells = new ArrayList<>();
        for(int j = 0; j < matrix.getColumnDimension(); j++){
            for(int i = 0; i < matrix.getRowDimension(); i++){
                cells.add(new HeatDataEntry(String.valueOf(j), String.valueOf(i), getData(i, j)));
            }
        }

        AnyChartView anyChartView = findViewById(R.id.chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        HeatMap heatMap = AnyChart.heatMap();

        heatMap.stroke("1 #fff");
        heatMap.hovered()
                .stroke("6 #fff")
                .fill(new SolidFill("#545f69", 1d))
                .labels("{ fontColor: '#fff' }");

        heatMap.interactivity().selectionMode(SelectionMode.NONE);

        heatMap.title().enabled(true);
        heatMap.title()
                .text("Теплова карта")
                .padding(0d, 0d, 20d, 0d);

        heatMap.labels().enabled(true);
        heatMap.labels()
                .minFontSize(14d)
                .format("function() {\n" +
                        "      var namesList = [\"Low\", \"Medium\", \"High\", \"Extreme\"];\n" +
                        "      return this.heat / 100;\n" +
                        "    }");

        heatMap.yAxis(0).stroke(null);
        heatMap.yAxis(0).labels().padding(0d, 15d, 0d, 0d);
        heatMap.yAxis(0).ticks(false);
        heatMap.xAxis(0).stroke(null);
        heatMap.xAxis(0).ticks(false);

        heatMap.data(cells);

        ScaleTypes scaleType = ScaleTypes.LINEAR_COLOR;
        heatMap.colorScale(scaleType);

        anyChartView.setChart(heatMap);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private double getElementValue(int i, int j){
        if(i >= matrix.getRowDimension() || j >= matrix.getColumnDimension()) return 0;
        if(i < 0 || j < 0) return 0;
        double value = matrix.get(i, j);
        return value;
    }

    private int getData(int i, int j){
        int value = (int)getElementValue(i, j) * 100;
        return value;
    }
}
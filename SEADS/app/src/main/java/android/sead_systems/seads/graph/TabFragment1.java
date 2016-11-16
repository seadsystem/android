package android.sead_systems.seads.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.sead_systems.seads.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class TabFragment1 extends Fragment {

    PieChart pieChart;
    public static  final int[] MY_COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64), Color.rgb(153,19,0),
            Color.rgb(38,40,53), Color.rgb(215,60,55)
    };

    WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);
/*
        pieChart = (PieChart) v.findViewById(R.id.piegraph);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(15.0f, "Car"));
        entries.add(new PieEntry(12.0f, "Bike"));
        entries.add(new PieEntry(15.8f, "Fridge"));
        entries.add(new PieEntry(65.0f, "Tesla Cannon"));
        entries.add(new PieEntry(11.2f, "TV"));

        PieDataSet pieset = new PieDataSet(entries, "Usage Breakdown");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : MY_COLORS)
            colors.add(c);
        pieset.setColors(colors);
        PieData piedata = new PieData(pieset);
        pieChart.setData(piedata);
*/
        mWebView = (WebView) v.findViewById(R.id.webviewgauge);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://people.ucsc.edu/~okdogulu/gauge.html");

        return v;
    }


}
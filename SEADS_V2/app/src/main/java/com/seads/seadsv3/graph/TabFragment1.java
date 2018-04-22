package com.seads.seadsv3.graph;

import android.graphics.Color;
import android.os.Bundle;
import com.seads.seadsv3.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.mikephil.charting.charts.PieChart;

/*
    Webview Daily Summary Fragment
 */
@Deprecated
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

        mWebView = (WebView) v.findViewById(R.id.webviewgauge);
        WebSettings webSettings = mWebView.getSettings();

        //Need to enable js to allow d3js draw animation
        webSettings.setJavaScriptEnabled(true);

        //Change loadUrl to adjust graph to settle
        mWebView.loadUrl("https://people.ucsc.edu/~okdogulu/gauge.html");
        mWebView.setVerticalScrollBarEnabled(false);

        return v;
    }


}
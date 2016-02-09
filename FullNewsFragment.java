package com.grand.capital.app.ui.news;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.grand.capital.app.R;
import com.grand.capital.app.WebInterfaceActivity;
import com.grand.capital.app.ui.BaseFragment;

/**
 * Created by lfqdt on 14.11.2015.
 */
public class FullNewsFragment extends BaseFragment {

    private String titleText;
    private String descriptionText;

    public static final FullNewsFragment newInstance(String title, String description){
        FullNewsFragment f = new FullNewsFragment();
        f.titleText = title;
        f.descriptionText = description;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_news, container, false);

        TextView title = (TextView) view.findViewById(R.id.newsTitle);
        WebView webView = (WebView) view.findViewById(R.id.webView2);

        title.setText(titleText);

        if(android.os.Build.MODEL.startsWith("Huawei")) {
            webView.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        }
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webView.loadDataWithBaseURL(WebInterfaceActivity.BASE_URL, descriptionText, "text/html", "utf-8", null);

        return view;
    }
}

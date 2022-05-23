package com.mirea.zarin.mireaproject.practice3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mirea.zarin.mireaproject.R;

public class Web extends Fragment implements View.OnClickListener
{
    EditText searchBar;
    ImageButton searchButton;
    ImageButton forwardButton;
    ImageButton backButton;
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        searchBar = (EditText) view.findViewById(R.id.searchBar);
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    webView.setVisibility(View.GONE);
                }
            }
        });        searchButton = (ImageButton) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        forwardButton = (ImageButton) view.findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(this);
        backButton = (ImageButton) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        webView = (WebView) view.findViewById(R.id.browser);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView v, String url)
            {
                super.onPageFinished(v, url);
                searchBar.setText(url);
            }
        });

        loadDefault();

        return view;
    }

    public void loadDefault()
    {
        String defaultUrl = "https://www.mirea.ru/";
        webView.loadUrl(defaultUrl);
    }

    public void loadUrl(String url)
    {
        if (!url.startsWith("https://"))
        {
            url = "https://" + url;
            webView.loadUrl(url);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.searchButton:
                String url = this.searchBar.getText().toString();
                loadUrl(url);
                webView.setVisibility(View.VISIBLE);
                break;
            case R.id.forwardButton:
                webView.goForward();
                break;
            case R.id.backButton:
                webView.goBack();
                break;
        }
    }
}
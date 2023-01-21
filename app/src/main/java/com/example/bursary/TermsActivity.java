package com.example.bursary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class TermsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        webView = findViewById(R.id.webView_tnc);
        webView.loadData(getString(R.string.privacy_policy), "text/html", "UTF-8");
    }
}
package com.example.bursary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.DialogFragment;

public class TermsAndConditions extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.terms_and_conditions, container, false);
        WebView webView = view.findViewById(R.id.webView);
        webView.loadData(getString(R.string.privacy_policy), "text/html", "UTF-8");
        return view;
    }
}

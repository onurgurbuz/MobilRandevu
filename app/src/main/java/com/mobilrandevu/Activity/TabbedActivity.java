package com.mobilrandevu.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.mobilrandevu.R;

public class TabbedActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        TabHost tabh = (TabHost) findViewById(android.R.id.tabhost);
        TabSpec tab1 = tabh.newTabSpec("tab menü 1. seçenek");
        TabSpec tab2 = tabh.newTabSpec("tab menü 2. seçenek");
        tab1.setIndicator("ÖĞRENCİ GİRİŞİ");
        tab1.setContent(new Intent(this, girisOgrenci.class));
        tab2.setIndicator("AKADEMİSYEN GİRİŞİ");
        tab2.setContent(new Intent(this, girisAkademisyen.class));
        tabh.addTab(tab1);
        tabh.addTab(tab2);
    }
}

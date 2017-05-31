package com.mobilrandevu.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mobilrandevu.DBHelper;
import com.mobilrandevu.Model.AkademisyenPOJO;
import com.mobilrandevu.Model.OgrenciPOJO;
import com.mobilrandevu.R;

public class KaydolActivity extends ActionBarActivity {
    private RadioButton rbOgrenci, rbAkademisyen;
    private EditText etOgrenciNo, etAdi, etParola;
    private Button btnKaydol;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(KaydolActivity.this);

        rbOgrenci = (RadioButton) findViewById(R.id.rbOgrenci);
        rbAkademisyen = (RadioButton) findViewById(R.id.rbAkademisyen);
        etOgrenciNo = (EditText) findViewById(R.id.etOgrenciNo);
        etAdi = (EditText) findViewById(R.id.etAdi);
        etParola = (EditText) findViewById(R.id.etParola);
        btnKaydol = (Button) findViewById(R.id.btnKaydol);

        rbAkademisyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etOgrenciNo.setHint("Email");
            }
        });

        rbOgrenci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etOgrenciNo.setHint("Öğrenci Numarası");
            }
        });

        btnKaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etOgrenciNo.getText().toString().trim().length() < 1 ||
                        etAdi.getText().toString().trim().length() < 1 ||
                        etParola.getText().toString().trim().length() < 1) {
                    Toast.makeText(KaydolActivity.this, "TÜM ALANLARI DOLDURUNUZ.", Toast.LENGTH_SHORT).show();
                } else {
                    if (rbOgrenci.isChecked()) {
                        OgrenciPOJO ogrenci = new OgrenciPOJO();
                        ogrenci.setOgrenciNumarasi(etOgrenciNo.getText().toString());
                        ogrenci.setAdi(etAdi.getText().toString());
                        ogrenci.setParola(etParola.getText().toString());
                        dbHelper.OgrenciEkle(ogrenci);
                        onBackPressed();
                    } else if (rbAkademisyen.isChecked()) {
                        AkademisyenPOJO akademisyen = new AkademisyenPOJO();
                        akademisyen.setEmail(etOgrenciNo.getText().toString());
                        akademisyen.setAdi(etAdi.getText().toString());
                        akademisyen.setParola(etParola.getText().toString());
                        dbHelper.AkademisyenEkle(akademisyen);
                        onBackPressed();
                    }
                }
            }
        });
    }
}

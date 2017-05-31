package com.mobilrandevu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilrandevu.BuildConfig;
import com.mobilrandevu.DBHelper;
import com.mobilrandevu.Model.OgrenciPOJO;
import com.mobilrandevu.R;
import com.mobilrandevu.Statikler;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class girisOgrenci extends FragmentActivity {
    private EditText etOgrenciNo, etParola;
    private CheckBox chkHatirla;
    private Button btnGiris, btnKaydol;
    private TextView tvVersion;
    private SpotsDialog dialog;

    private Intent intent;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean hatirla;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_ogrenci);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dbHelper = new DBHelper(girisOgrenci.this);

        etOgrenciNo = (EditText) findViewById(R.id.etOgrenciNo);
        etParola = (EditText) findViewById(R.id.etParola);
        chkHatirla = (CheckBox) findViewById(R.id.chkHatirla);
        btnGiris = (Button) findViewById(R.id.btnGiris);
        btnKaydol = (Button) findViewById(R.id.btnKaydol);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvVersion.setText("v" + BuildConfig.VERSION_NAME);

        intent = new Intent(girisOgrenci.this, MainActivity.class);
        dialog = new SpotsDialog(girisOgrenci.this, R.style.Custom);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        hatirla = loginPreferences.getBoolean("hatirla", false);

        if (hatirla == true) {
            etOgrenciNo.setText(loginPreferences.getString("ogrencino", ""));
            etParola.setText(loginPreferences.getString("parola", ""));
            chkHatirla.setChecked(true);
        }

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkHatirla.isChecked()) {
                    loginPrefsEditor.putBoolean("hatirla", true);
                    loginPrefsEditor.putString("ogrencino", etOgrenciNo.getText().toString());
                    loginPrefsEditor.putString("parola", etParola.getText().toString());
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                dialog.show();
                List<OgrenciPOJO> ogrenci = new ArrayList<OgrenciPOJO>();
                ogrenci = dbHelper.OgrenciGetir(etOgrenciNo.getText().toString(), etParola.getText().toString());
                if (ogrenci.size() > 0) {
                    Statikler.tip = 0;
                    Statikler.ogrenciID = ogrenci.get(0).getId();
                    dialog.dismiss();
                    startActivity(intent);
                } else {
                    Toast.makeText(girisOgrenci.this, "ÖĞRENCİ NUMARASI YA DA PAROLA HATALI.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btnKaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(girisOgrenci.this, KaydolActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Çıkış Yapılsın mı?").setCancelable(false).setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

            }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialogBuilder.create().show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}

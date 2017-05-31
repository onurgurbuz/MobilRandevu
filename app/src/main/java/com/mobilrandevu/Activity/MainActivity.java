package com.mobilrandevu.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mobilrandevu.BuildConfig;
import com.mobilrandevu.DBHelper;
import com.mobilrandevu.Fragment.GecmisRandevular;
import com.mobilrandevu.Fragment.GelecekRandevular;
import com.mobilrandevu.Fragment.RandevuAl;
import com.mobilrandevu.Model.AkademisyenPOJO;
import com.mobilrandevu.R;
import com.mobilrandevu.Statikler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private Intent intent;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private MenuItem navRandevuAl, navGecmisRandevular, navGelecekRandevular;
    private FragmentTransaction xfragmentTransaction;
    private DBHelper dbHelper;

    private List<AkademisyenPOJO> akademisyenPOJOs = new ArrayList<AkademisyenPOJO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        mFragmentManager = getSupportFragmentManager();
        mNavigationView.setItemIconTintList(null);

        Menu menuNav = mNavigationView.getMenu();
        navRandevuAl = menuNav.findItem(R.id.navRandevuAl);
        navGecmisRandevular = menuNav.findItem(R.id.navGecmisRandevular);
        navGelecekRandevular = menuNav.findItem(R.id.navGelecekRandevular);

        if (Statikler.tip == 1)
            navRandevuAl.setVisible(false);
        else
            navRandevuAl.setVisible(true);

        getSupportFragmentManager().beginTransaction().add(R.id.containerView, new GecmisRandevular()).commit();

        dbHelper = new DBHelper(MainActivity.this);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                xfragmentTransaction = mFragmentManager.beginTransaction();
                Statikler.secilenAkademisyen = null;
                if (menuItem.getItemId() == R.id.navGecmisRandevular) {
                    xfragmentTransaction.replace(R.id.containerView, new GecmisRandevular()).commit();
                } else if (menuItem.getItemId() == R.id.navGelecekRandevular) {
                    xfragmentTransaction.replace(R.id.containerView, new GelecekRandevular()).commit();
                } else if (menuItem.getItemId() == R.id.navRandevuAl) {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialog);
                    final ListView lvItems = (ListView) dialog.findViewById(R.id.lvItems);
                    akademisyenPOJOs = dbHelper.AkademisyenleriGetir();
                    List<String> akademisyenler = new ArrayList<String>();
                    for (AkademisyenPOJO akademisyen : akademisyenPOJOs)
                        akademisyenler.add(akademisyen.getAdi());

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, akademisyenler);
                    lvItems.setAdapter(adapter);
                    dialog.show();
                    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Statikler.secilenAkademisyen = akademisyenPOJOs.get(i);
                            dialog.dismiss();
                            xfragmentTransaction.replace(R.id.containerView, new RandevuAl()).commit();
                        }
                    });
                } else if (menuItem.getItemId() == R.id.navHakkimizda) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    String mesaj = String.valueOf(Html.fromHtml("Buse, Tolga" + BuildConfig.VERSION_NAME));
                    builder.setMessage(mesaj)
                            .setPositiveButton(getString(R.string.kapat), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                } else if (menuItem.getItemId() == R.id.navCikis) {
                    try {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle(getString(R.string.cikisyapilsinmi))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.evet), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                                        loginPrefsEditor = loginPreferences.edit();
                                        loginPrefsEditor.remove("otomatikgiris");
                                        loginPrefsEditor.commit();
                                        dialog.dismiss();
                                        intent = new Intent(MainActivity.this, TabbedActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).setNegativeButton(getString(R.string.hayir), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialogBuilder.create().show();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.cikisyapilsinmi))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.evet), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                            loginPrefsEditor = loginPreferences.edit();
                            loginPrefsEditor.remove("otomatikgiris");
                            loginPrefsEditor.commit();
                            dialog.dismiss();
                            intent = new Intent(MainActivity.this, TabbedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton(getString(R.string.hayir), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogBuilder.create().show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

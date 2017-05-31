package com.mobilrandevu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobilrandevu.Adapter.GelecekRandevularListAdapter;
import com.mobilrandevu.DBHelper;
import com.mobilrandevu.Model.RandevuPOJO;
import com.mobilrandevu.R;
import com.mobilrandevu.Statikler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class GelecekRandevular extends Fragment {
    private ListView lvRandevular;
    private GelecekRandevularListAdapter gelecekRandevularListAdapter;
    private List<RandevuPOJO> listeRandevular = new ArrayList<RandevuPOJO>();
    private Toolbar toolbar;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gelecekrandevular, container, false);
        lvRandevular = (ListView) view.findViewById(R.id.lvRandevular);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gelecek Randevular");
        dbHelper = new DBHelper(getContext());
        List<RandevuPOJO> randevular = new ArrayList<RandevuPOJO>();
        if (Statikler.tip == 0)
            randevular = dbHelper.RandevulariGetirByTip(Statikler.ogrenciID);
        else if (Statikler.tip == 1)
            randevular = dbHelper.RandevulariGetirByTip(Statikler.akademisyenID);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        for (RandevuPOJO randevu : randevular) {
            try {
                String d = randevu.getTarih() + " " + randevu.getSaat();
                Date dateRandevu = dateFormat.parse(d);
                if (System.currentTimeMillis() < dateRandevu.getTime()) {
                    listeRandevular.add(randevu);
                }
            } catch (ParseException e) {
                Log.e("TAG", e.getMessage());
            }
        }
        gelecekRandevularListAdapter = new GelecekRandevularListAdapter(getContext(), listeRandevular);
        lvRandevular.setAdapter(gelecekRandevularListAdapter);

        return view;
    }
}

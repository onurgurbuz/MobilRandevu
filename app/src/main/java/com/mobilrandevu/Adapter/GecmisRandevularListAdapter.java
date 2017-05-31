package com.mobilrandevu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobilrandevu.DBHelper;
import com.mobilrandevu.Model.AkademisyenPOJO;
import com.mobilrandevu.Model.OgrenciPOJO;
import com.mobilrandevu.Model.RandevuPOJO;
import com.mobilrandevu.R;
import com.mobilrandevu.Statikler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GecmisRandevularListAdapter extends ArrayAdapter<RandevuPOJO> {

    private List<RandevuPOJO> randevular;
    private ArrayList<RandevuPOJO> arrayList;
    private DBHelper dbHelper;

    public GecmisRandevularListAdapter(Context context, List<RandevuPOJO> listeRandevular) {
        super(context, R.layout.listadapter_cardview, listeRandevular);
        randevular = listeRandevular;
        arrayList = new ArrayList<RandevuPOJO>();
        arrayList.addAll(randevular);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public int getCount() {
        return randevular.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.listadapter_cardview, parent, false);

        TextView tvRandevuTarihi = (TextView) view.findViewById(R.id.tvRandevuTarihi);
        TextView tvAkademisyen = (TextView) view.findViewById(R.id.tvAkademisyen);

        RandevuPOJO randevu = randevular.get(position);
        tvRandevuTarihi.setText(randevu.getTarih() + " " + randevu.getSaat());
        List<OgrenciPOJO> ogrenci = new ArrayList<OgrenciPOJO>();
        List<AkademisyenPOJO> akademisyen = new ArrayList<AkademisyenPOJO>();
        if (Statikler.tip == 0) {
            akademisyen = dbHelper.AkademisyenGetirByID(randevu.getAkademisyenID());
            tvAkademisyen.setText(akademisyen.get(0).getAdi());
        } else if (Statikler.tip == 1) {
            ogrenci = dbHelper.OgrenciGetirByID(randevu.getOgrenciID());
            tvAkademisyen.setText(ogrenci.get(0).getOgrenciNumarasi() + "-" + ogrenci.get(0).getAdi());
        }
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        randevular.clear();
        if (charText.length() == 0) {
            randevular.addAll(arrayList);
        } else {
            for (RandevuPOJO wp : arrayList) {
                if (wp.getTarih().toLowerCase(Locale.getDefault()).contains(charText)) {
                    randevular.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

